<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        td {
            padding-right: 10px;
        }
        img {
            position: relative;
            top: 3px;
        }
    </style>
    <title>Directories</title>
</head>
<body>
<div style="margin-left: 10px; font-size: 20pt;">${now}</div>
<div style="margin-left: 13px ; font-size: 20pt;"> ${button} </div>
<h1 style="margin-left: 10px">${name}</h1>

<div class="content" style="margin-left: 10px">
    <table>
        <tr>
            <th>Файл</th>
            <th>Размер</th>
            <th>Дата</th>
        </tr>
        ${folders}
        ${files}
    </table>
</div>
</body>
</html>