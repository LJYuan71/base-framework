//alert的demo
/*Modal.alert({
    msg: '请填写正确页面',
	title: '标题',
	btnok: '确定',
	btncl:'取消'
 });*/

//confirm的demo
//如需增加回调函数，后面直接加 .on( function(e){} );
// 点击“确定” e: true
// 点击“取消” e: false
/*Modal.confirm({msg: "是否删除角色？"}).on( function (e) {
    alert("返回结果：" + e);
});

//倒计时小时
Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
	//消失后执行的方法
});
*/
$(function() {
	/**
	 * 项目公共alert、confirm、倒计时提示窗口modal 开始
	 */ 
	window.Modal = function() {
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var alr = $("#ycf-alert");
		var ahtml = alr.html();
		//alert实现
		var _alert = function(options) {
			alr.html(ahtml); // 复原
			alr.find('.ok').removeClass('btn-success').addClass('btn-primary');
			alr.find('.cancel').hide();
			_dialog(options);
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						alr.find('.ok').click(function() {
							callback(true)
						});
					}
				}
			};
		};
		//alert   n秒后自动关闭实现
		var _timealert = function(options) {
			alr.html(ahtml); // 复原
			alr.find('.ok').hide();
			alr.find('.cancel').hide();
			_dialog(options);
			setTimeout(function(){$("#ycf-alert").modal("hide")},options.time);
			return {
				on : function(callback) {
					setTimeout(function(){callback(true)},options.time);
				}
			};
		};
		//confirm实现
		var _confirm = function(options) {
			alr.html(ahtml); // 复原
			alr.find('.ok').removeClass('btn-primary').addClass('btn-success');
			alr.find('.cancel').show();
			_dialog(options);
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						alr.find('.ok').click(function() {
							callback(true)
						});
						alr.find('.cancel').click(function() {
							callback(false)
						});
					}
				}
			};
		};
		//dialog实现
		var _dialog = function(options) {
			var ops = {
				msg : "提示内容",
				title : "操作提示",
				btnok : "确定",
				btncl : "取消",
				time  : "消失时间"
			};
			$.extend(ops, options);
			var html = alr.html().replace(reg, function(node, key) {
				return {
					Title : ops.title,
					Message : ops.msg,
					BtnOk : ops.btnok,
					BtnCancel : ops.btncl
				}[key];
			});

			alr.html(html);
			alr.modal({
				width : 500
				/*,
				backdrop : 'static'*/
			});
		}
		return {
			alert : _alert,
			confirm : _confirm,
			timealert:_timealert
		}
	}();
	/**
	 * 项目公共alert、confirm、倒计时提示窗口modal 结束
	 */
	/**
	 * 编辑页面弹出modal 开始
	 */
	window.dialogModal = function() {
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var editPage = $("#editPage");
		var ahtml = editPage.html();
		//编辑页面Dialog实现
		var _editPageDialog = function(options) {
			editPage.html(ahtml); // 复原
			_dialog(options);
		};
		//dialog实现
		var _dialog = function(options) {
			var ops = {
				url  : "dialog打开的链接",
				editLayout:"布局sm/md/lg"
				
			};
			$.extend(ops, options);
			var html = editPage.html().replace(reg, function(node, key) {
				return {
					editLayout : ops.editLayout
				}[key];
			});
			editPage.html(html);
			//清空上次加载的缓存
			editPage.removeData('bs.modal');
			editPage.modal({
				remote:ops.url
			});
		}
		return {
			editPageDialog : _editPageDialog
		}
	}();
	/**
	 * 编辑页面弹出modal 结束
	 */
	/**
	 * 编辑页面弹出层中新的树弹出modal 开始
	 */
	window.treeModal = function() {
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var treeModal = $("#treeModal");
		var ahtml = treeModal.html();
		//编辑页面Dialog实现
		var _treeDialog = function(options) {
			treeModal.html(ahtml); // 复原
			_dialog(options);
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						treeModal.find('.treeOk').click(function() {
							callback(true)
						});
						treeModal.find('.treeCancel').click(function() {
							callback(false)
						});
					}
				}
			};
		};
		//dialog实现
		var _dialog = function(options) {
			var ops = {
					treeLayout:"布局sm/md/lg",
					treeTitle :"请选择",
					treeBtnOk :"确定",
					treeBtnCancel:"取消",
					isShow	: "show",
					treeId	:"treeId",
					treeHight:"300"
					
			};
			$.extend(ops, options);
			var html = treeModal.html().replace(reg, function(node, key) {
				return {
					treeLayout : ops.treeLayout,
					treeTitle	:ops.treeTitle,
					treeBtnOk	:ops.treeBtnOk,
					treeBtnCancel:ops.treeBtnCancel,
					treeId		:ops.treeId,
					treeHight	:ops.treeHight
				}[key];
			});
			treeModal.html(html);
			//清空上次加载的缓存
			/*treeModal.removeData('bs.modal');
			treeModal.modal({
				remote:ops.url
			});*/
			treeModal.modal(ops.isShow);
		}
		return {
			treeDialog : _treeDialog
		}
	}();
	/**
	 * 编辑页面弹出层中新的树弹出modal 结束
	 */
	/**
	 * 编辑页面弹出modal，二次弹出 开始
	 */
	window.dialogPopupModal = function() {
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var editPagePopup = $("#editPagePopup");
		var ahtml = editPagePopup.html();
		//编辑页面Dialog实现
		var _editPagePopup = function(options) {
			editPagePopup.html(ahtml); // 复原
			_dialog(options);
		};
		//dialog实现
		var _dialog = function(options) {
			var ops = {
				url  : "dialog打开的链接",
				editPopupLayout:"布局sm/md/lg"
				
			};
			$.extend(ops, options);
			var html = editPagePopup.html().replace(reg, function(node, key) {
				return {
					editPopupLayout : ops.editPopupLayout
				}[key];
			});
			editPagePopup.html(html);
			//清空上次加载的缓存
			editPagePopup.removeData('bs.modal');
			editPagePopup.modal({
				remote:ops.url
			});
		}
		return {
			editPagePopup : _editPagePopup
		}
	}();
	/**
	 * 编辑页面弹出modal，二次弹出 结束
	 */
	
});