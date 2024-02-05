<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Appointments</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<h1>Appointments</h1>
<table id="appointmentsTable">
    <thead>
        <tr>
            <th>Date</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
                <td>${appointment.value}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>