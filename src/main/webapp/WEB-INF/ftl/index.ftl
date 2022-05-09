<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>CTGU办公OA系统</title>
    <link rel="stylesheet" href="../../resources/layui/css/layui.css">
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo layui-hide-xs layui-bg-black">CTGU办公OA系统</div>
        <!-- 头部区域（可配合layui 已有的水平导航） -->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item layui-hide layui-show-md-inline-block">
                <a href="javascript:;">
                    <i class="layui-icon layui-icon-user"></i>
                    ${current_employee.name} [${current_department.departmentName}-${current_employee.title}]
                </a>
                <dl class="layui-nav-child">
<!--                    <dd><a href="">个人资料</a></dd>-->
<!--                    <dd><a href="">设置</a></dd>-->
                    <dd><a href="/logout">注销</a></dd>
                </dl>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree">
                <#list node_list as parent>
                    <#if parent.nodeType == 1>
                        <li class="layui-nav-item layui-nav-itemed">
                            <a class="" href="javascript:;">${parent.nodeName}</a>
                            <dl class="layui-nav-child">
                                <#list node_list as node>
                                    <#if node.nodeType == 2 && node.parentId == parent.nodeId>
                                        <dd><a href="${node.url}" target="ifmMain">${node.nodeName}</a></dd>
                                    </#if>
                                </#list>
                            </dl>
                        </li>
                    </#if>
                </#list>

            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <iframe name="ifmMain" width="99.6%" height="100%" src="/forward/notice"></iframe>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        Copyright ©2022 KAIXIN. All Rights Reserved.
    </div>
</div>
<script src="../../resources/layui/layui.js"></script>
<script>

</script>
</body>
</html>