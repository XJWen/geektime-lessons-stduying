<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>注册页面</title>
</head>
<body>
    <div class="container">
        <form class="form-group" align="center" method="post">
           <h1 class="h3 mb-3 font-weight-normal">注册</h1>
            <form action="/register" method="post" class="form-group">
                <label>用户名:</label><input type="text" name="username">
                <br>
                <label>密码:</label><input type="text" name="password">
                <input type="submit" value="注册">
            </form>
        </form>
    </div>
</body>
</html>
