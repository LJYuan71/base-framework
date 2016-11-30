<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 多层modal -->
<div class="container ">
	<!-- 编辑页面弹出modal 开始 -->
	<div class="modal fade  bs-example-modal-[editLayout]" id="editPage" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog  modal-[editLayout]">
	      <div class="modal-content">
	         <div class="modal-body">
	            	填充的内容
	         </div>
	      </div>
	    </div>
	</div>
	<!-- 编辑页面弹出modal 结束  -->
	<!-- 编辑页面弹出modal，二次弹出   开始 -->
	<div class="modal fade  bs-example-modal-[editPopupLayout]" id="editPagePopup" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog  modal-[editPopupLayout]">
	      <div class="modal-content">
	         <div class="modal-body">
	            	填充的内容
	         </div>
	      </div>
	    </div>
	</div>
	<!-- 编辑页面弹出modal，二次弹出   结束  -->
	<!-- 编辑页面弹出层中新的树弹出modal 开始  -->
	<div class="modal fade  bs-example-modal-[treeLayout]" id="treeModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog  modal-[treeLayout]">
	    	<div class="modal-content">
	        	<div class="modal-header" style="padding:5px;">
	            	<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h5 class="modal-title">
						<i class="fa fa-exclamation-circle"></i> [treeTitle]
					</h5>
				</div>
		        <div class="modal-body">
		            <div class="zTreeDemoBackground left">
		                <ul id="[treeId]" class="ztree" style="max-height:[treeHight]px;width:auto; overflow-y:scroll;"></ul>
		            </div>
		        </div>
		        <div class="modal-footer" style="padding:5px;">
		         	<button type="button" class="btn btn-primary treeOk"
							data-dismiss="modal">[treeBtnOk]</button>
						<button type="button" class="btn btn-default treeCancel"
							data-dismiss="modal">[treeBtnCancel]</button>
		        </div>
			</div>
	    </div>
	</div> 
	<!-- 编辑页面弹出层中新的树弹出modal 结束  -->
	<!-- 项目公共alert、confirm、倒计时提示窗口modal 开始 -->
	<div id="ycf-alert" class="modal fade " tabindex="-1">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h5 class="modal-title">
						<i class="fa fa-exclamation-circle"></i> [Title]
					</h5>
				</div>
				<div class="modal-body small">
					<p style="font-size: 15px;">[Message]</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary ok"
						data-dismiss="modal">[BtnOk]</button>
					<button type="button" class="btn btn-default cancel"
						data-dismiss="modal">[BtnCancel]</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 项目公共alert、confirm、倒计时提示窗口modal 结束 -->
</div>