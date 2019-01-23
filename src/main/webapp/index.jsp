<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sign in</title>
</head>
<body>
    <h3 align="center">Sign in</h3>
    <form name="loginForm" action="login" method="POST">
        <table border="0" align="center">
            <tbody>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td><input id="username" name="username" type="text" /></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input id="password" name="password" type="password" /></td>
                </tr>
                <tr>
                    <td align="center"><input name="Submit" type="Submit" value="Sign in" /></td>
                </tr>
                <tr>
                    <td align="right"><a href="/registration">Sign up</a></td>
                </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
