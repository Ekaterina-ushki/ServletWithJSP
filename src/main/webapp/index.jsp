<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8"/>
    <title>Вход</title>
</head>
<body>
    <form action="" method="POST">
        Login: <input type="text" name="login"/><br><br>
        Password: <input type="password" name="pass"/><br><br>
        <input type="submit" value="Sign in">
    </form>
    <div class="button">
        <p class="button"><a href="http://localhost:8080/ServletWithJSP_war/registration" target="_self">Sign UP</a></p>
    </div>
</body>
</html>
