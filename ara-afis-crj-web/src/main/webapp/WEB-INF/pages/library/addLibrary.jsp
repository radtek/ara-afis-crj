<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <head>

    </head>

<body>
<div class="hideDiv">
    <input type="hidden" id="action" value="save"/>
    <%--页面前缀--%>
    <input type="hidden" id="prifex" value="taslibrary"/>
    <input type="hidden" id="page" value="Taslibrary"/>
    <input type="hidden" id="operate" value="">
    <input type="hidden" id="switchInitStat" value="${manager.statu}">
    <input type="hidden" id="selectInitStat" value="${manager.role.id}">
</div>

<section class="wrapper_top">
    <div class="row">
        <div class="col-lg-12">
            <!--breadcrumbs start -->
            <ul class="breadcrumb">
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i
                        class="icon-home"></i>首页</a></li>
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','taslibrary/toGetList','');">
                    <i class="icon-hand-right"></i>管理指纹库别</a></li>
                <li class="active"><i class="icon-pencil"></i>添加指纹库别</li>
            </ul>
            <!--breadcrumbs end -->
        </div>
    </div>
</section>

<section class="wrapper">

    <div class="row">
        <div class="col-lg-12">
            <div id="result" class="alert alert-block fade in hideDiv">
                <button data-dismiss="alert" class="close close-sm" type="button">
                    <i class="icon-remove"></i>
                </button>
                <h4><i id="result_sign"></i><span id="result_title"></span></h4>
                <p id="result_msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading panel-bg-orgin">
                    <i class="icon-book"></i>添加指纹库别
                </header>
                <div class="panel-body">
                    <div class="form text-nowrap">
                        <form class="cmxform form-horizontal tasi-form" id="dataForm" name="dataForm"
                              novalidate="novalidate">
                            <input type="hidden" id="anyOption" name="anyOption"/>
                            <input type="hidden" id="anyId" name="anyId"/>
                            <div class="form-group ">
                                <label for="libraryId" class="control-label col-lg-1"><span class="form-req">*</span>指纹库ID ：</label>
                                <div class="col-lg-4">
                                    <input id="libraryId" name="libraryId" class="form-control"
                                           type="text" maxlength="30" required data-rule-onlyNumChar="true"
                                           data-msg-onlyNumChar="非有效的ID，只能包含：数字和字母">
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="verifyLibraryGoal" class="control-label col-lg-1"><span class="form-req">*</span>1:1比对阈值 ：</label>
                                <div class="col-lg-4">
                                    <input id="verifyLibraryGoal" name="verifyLibraryGoal" class="form-control"
                                           type="text"
                                           data-rule-onlyNum0_1000="true"
                                           data-msg-onlyNum0_1000="非有效的数字，只能包含：0-1000数字">
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="libraryId" class="control-label col-lg-1"><span class="form-req">*</span>1:N比对阈值 ：</label>
                                <div class="col-lg-4">
                                    <input id="identifyLibraryGoal" name="identifyLibraryGoal" class="form-control"
                                           type="text"
                                           data-rule-onlyNum0_1000="true"
                                           data-msg-onlyNum0_1000="非有效的数字，只能包含：0-1000数字">
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="libraryId" class="control-label col-lg-1"><span class="form-req">*</span>指纹库描述 ：</label>
                                <div class="col-lg-4">
                                    <input id="libraryDesc" name="libraryDesc" class="form-control"
                                           type="text">
                                </div>
                            </div>


                            <div class="form-group ">
                                <label for="libraryActivietyFlag" class="control-label col-lg-1"><span class="form-req">*</span>指纹库状态
                                    ：</label>
                                <div class="col-lg-11">
                                    <div class="switch" data-on="success" data-off="danger" data-on-label="可用"
                                         data-off-label="停用">
                                        <input type="checkbox" id="libraryActivietyFlag" name="libraryActivietyFlag" checked/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-1 col-lg-12">
                                    <button class="btn btn-danger btn-form" type="button" id="button_add">
                                        <i class="icon-ok-sign"></i>
                                        添加
                                    </button>
                                    <button class="btn btn-danger btn-form"
                                            onclick="loadPage('main-content','taslibrary/toGetList','');"
                                            type="button" >
                                        <i class="icon-reply"></i> 返回
                                    </button>
                                    <button class="btn btn-default btn-form" type="button" id="button_reset">
                                        <i class="icon-refresh"></i> 重置
                                    </button>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th class="col-lg-1"><i class="icon-bullhorn"></i> 指纹库ID</th>
                        <th class="col-lg-1"><i class="icon-bookmark"></i> 1:1比对阈值</th>
                        <th class="col-lg-1"><i class="icon-question-sign"></i> 1:N比对阈值</th>
                        <th class="col-lg-1"><i class="icon-off"></i> 指纹库状态</th>
                        <th class="col-lg-2"><i class=" icon-calendar"></i> 指纹库描述</th>
                        <th class="col-lg-1"><i class=" icon-calendar"></i> 更新时间</th>
                        <th class="col-lg-1"><i class=" icon-calendar"></i> 创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="tabBody">
                    </tbody>
                </table>
            </section>
        </div>
    </div>
</section>
<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
