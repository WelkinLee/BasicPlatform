<%--
  Created by IntelliJ IDEA.
  User: yangyang
  Date: 14-9-28
  Time: 上午12:24
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>图书管理系统-首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="nivo/themes/default/default.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="nivo/themes/light/light.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="nivo/themes/dark/dark.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="nivo/themes/bar/bar.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="nivo/nivo-slider.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="nivo/style.css" type="text/css" media="screen" />

    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <!--<script src="js/jquery-1.10.2.js" type="text/javascript"></script>-->
    <script src="js/jquery.json-2.4.min.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>

    <script src="lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerRadio.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerSpinner.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerPanel.js" type="text/javascript"></script>

    <script src="js/config.js" type="text/javascript"></script>

    <script type="text/javascript" src="nivo/jquery.nivo.slider.js"></script>

    <script type="text/javascript">
        $(function ()
        {
            $("#layout1").ligerLayout({topHeight:68});
            $("#titleContainer").load("title.html");
            $("#mainContainer").load("dashboardUser/layout.html");
        });

    </script>
    <style type="text/css">
        body{ padding:0px; margin:0;overflow: hidden;}
        #layout1{  width:100%; margin:40px;  height:400px;
            margin:0; padding:0;}
        #accordion1 { height:270px;}
        h4{ margin:20px;}
    </style>
</head>
<body>
<div id="layout1">
    <div id="mainContainer" position="center" title="" style="overflow: auto;">
        <h4>$("#layout1").ligerLayout(); </h4>
        默认为固定layout的高度百分一百
    </div>
    <!--<div position="right"></div>-->
    <div id="titleContainer" position="top"></div>
</div>

<div style="display:none;">

</div>
</body>
</html>
