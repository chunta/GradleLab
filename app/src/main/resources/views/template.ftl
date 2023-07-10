<!DOCTYPE html>
<html>
<head>
    <title>Record List</title>
    <style>
        .red {
            color: red;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            border: 1px solid black;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
        }
    </style>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Age</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <#list records as record>
        <tr>
            <td>${record.name}</td>
            <td<#if record.age < 20> class="red"</#if>>${record.age}</td>
            <td>${record.email}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>