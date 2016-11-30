package com.platform.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClientUtils {

	public String username;
	public String password;
	public String server;
	public int port;
	public FTPClient ftpClient;
	public boolean asciiTransfer = false;

	private final Log logger = LogFactory.getLog(FtpClientUtils.class);

	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param localFile
	 *            本地文件名(包括完整路径)
	 * @param remoteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功返回true，失败返回false
	 */
	public boolean uploadFile(String localFile, String remoteFile) {
		InputStream input = null;
		try {
			input = new FileInputStream(localFile);
		} catch (FileNotFoundException e) {
			logger.debug("上传文件不存在", e);
			return false;
		}
		return uploadFile(input, remoteFile);
	}

	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param localFile
	 *            本地文件名(包括完整路径)
	 * @param remoteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功返回true，失败返回false
	 */
	public boolean uploadFile(File localFile, String remoteFile) {
		InputStream input = null;
		try {
			input = new FileInputStream(localFile);
		} catch (FileNotFoundException e) {
			logger.debug("上传文件不存在", e);
			return false;
		}
		return uploadFile(input, remoteFile);
	}

	/**
	 * 将目录上传到ftp
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void uploadDir(File localDir, String path) {
		List<File> files = FileUtil.getFileOnly(localDir);
		for (File file : files) {
			String relativePath = file.getAbsolutePath().substring(
					file.getAbsolutePath().indexOf(localDir.getAbsolutePath()) + localDir.getAbsolutePath().length());
			uploadFile(file.getAbsolutePath(), path + relativePath);
		}
	}

	/**
	 * 从文件流上传文件到远程指定文件
	 * 
	 * @param input
	 *            文件流 InputStream
	 * @param remoteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功返回true，失败返回false
	 */
	public boolean uploadFile(InputStream input, String remoteFile) {
		try {
			if (!connect()) {
				return false;
			}
			// 设置文件传输类型
			if (asciiTransfer) {
				ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			} else {
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
			// 处理传输
			File file = new File(remoteFile);
			String parent = file.getParent();
			
			markDir(ftpClient, file.getParentFile());
			
			if (!ftpClient.changeWorkingDirectory(parent)) {
				boolean flag = ftpClient.makeDirectory(parent);
				if (!flag) {
					return false;
				}
			}
			ftpClient.changeWorkingDirectory("/");
			ftpClient.storeFile(remoteFile, input);
			input.close();
			disconnect();
			return true;
		} catch (IOException e) {
			logger.debug("不能上传文件到FTP服务器", e);
		}
		disconnect();
		return false;
	}
	
	/**
	 * 递归创建目录
	 * @throws IOException 
	 */
	private void markDir(FTPClient ftpClient,File directory) throws IOException{
		String[] paths = null;
		if(File.separator.equals("\\")){
			paths = directory.getPath().replace("\\", "/").split("/");
		}else {
			paths = directory.getPath().split("/");
		}
		ftpClient.changeWorkingDirectory("/");
		for(String str:paths){
			if (StringUtils.isNotBlank(str)){ 
				ftpClient.makeDirectory(str);
				ftpClient.changeWorkingDirectory(str);
			}
		}
		
	}
	
	/**
	 * 下载一个远程文件到本地的指定文件
	 * 
	 * @param localFile
	 *            本地文件名(包括完整路径)
	 * @param remoteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功返回true，失败返回false
	 */
	public boolean downloadFile(String localFile, String remoteFile) {
		OutputStream output = null;
		try {
			if (!connect()) {
				return false;
			}
			// 设置文件传输类型
			if (asciiTransfer) {
				ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			} else {
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
			output = new FileOutputStream(localFile);
			ftpClient.retrieveFile(remoteFile, output);
			output.close();
			disconnect();
			return true;
		} catch (FileNotFoundException e) {
			logger.debug("文件不存在", e);
		} catch (IOException e) {
			logger.debug("不能下载文件", e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
			}
		}
		disconnect();
		return false;
	}

	public boolean downloadFileToStream(OutputStream output, String remoteFile) {
		try {
			if (!connect()) {
				return false;
			}
			ftpClient.retrieveFile(remoteFile, output);
			disconnect();
			return true;
		} catch (IOException e) {
			logger.debug("不能下载文件", e);
			disconnect();
			return false;
		}
	}

	/**
	 * 删除一个远程文件
	 * 
	 * @param remoteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功返回true，失败返回false
	 */
	public boolean deleteFile(String remoteFile) {
		try {
			if (!connect()) {
				return false;
			}
			ftpClient.deleteFile(remoteFile);
			disconnect();
			return true;
		} catch (IOException e) {
			logger.debug("删除文件失败", e);
		}
		disconnect();
		return false;
	}

	/**
	 * 移动远程文件到指定文件夹
	 */
	public boolean moveFile(String from, String to) {
		try {
			if (!connect()) {
				return false;
			}
			boolean flag = isExist(from);
			if (!flag) {
				disconnect();
				return false;
			}
			uploadFile(ftpClient.retrieveFileStream(from), to);
			deleteFile(from);
			return true;
		} catch (IOException e) {
			logger.debug("移动文件失败", e);
		}
		disconnect();
		return false;
	}

	/**
	 * 复制远程指定文件到一个新文件
	 */
	public boolean copyFile(String from, String to) {
		try {
			if (!connect()) {
				return false;
			}
			boolean flag = isExist(from);
			if (!flag) {
				disconnect();
				return false;
			}
			uploadFile(ftpClient.retrieveFileStream(from), to);
			return true;
		} catch (IOException e) {
			logger.debug("复制文件失败", e);
		}
		disconnect();
		return false;
	}

	/**
	 * 重命名一个远程文件
	 * 
	 * @param from
	 *            该远程文件名重新命名
	 * 
	 * @param to
	 *            该远程文件的新名称
	 * 
	 * @return 成功返回true，失败返回false
	 */
	public boolean rename(String from, String to) {
		try {
			if (!connect()) {
				return false;
			}
			boolean flag = isExist(from);
			if (!flag) {
				disconnect();
				return false;
			}
			ftpClient.rename(from, to);
			disconnect();
			return true;
		} catch (IOException e) {
			logger.debug("重命名失败", e);
		}
		disconnect();
		return false;
	}

	/**
	 * 连接FTP
	 * 
	 * @throws IOException
	 */
	public boolean connect() throws IOException {

		int reply;
		try {
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.setDefaultPort(port);
			ftpClient.setBufferSize(1024);
			ftpClient.connect(server);
			reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftpClient.login(username, password)) {
					ftpClient.enterLocalPassiveMode();
					return true;
				}
			} else {
				ftpClient.disconnect();
				logger.debug("FTP服务器拒绝连接");
			}
		} catch (IOException e) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
				}
			}
			logger.error("FTP服务器拒绝连接或FTP服务器不存在");
			throw new IOException();
		}
		return false;
	}

	/**
	 * 断开FTP连接
	 */
	public void disconnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				if (ftpClient.isConnected()) {
					ftpClient.disconnect();
				}
			}
		} catch (IOException e) {
			logger.error("不能断开FTP服务器的连接", e);
		}
	}

	private boolean isExist(String file) throws IOException {
		int count = ftpClient.listFiles(file).length;
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param server
	 *            ftp地址
	 * @param port
	 *            ftp端口
	 * 
	 */
	public FtpClientUtils(String username, String password, String server, int port) {
		this.username = username;
		this.password = password;
		this.server = server;
		this.port = port;
	}

	public static void main(String[] args) {
		FtpClientUtils ftpClientUtils = new FtpClientUtils("zhangxin", "123456", "127.0.0.1", 21);
		try {
			ftpClientUtils.connect();
			ftpClientUtils.uploadFile("F:\\workspacesJdk7\\casec_base\\web\\upload\\jsp_source_2016011916033419867l3l8hc.jpg", "/1.jpg");
			//ftpClientUtils.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
