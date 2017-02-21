<!--
  ____                 _                  
 |  _ \ _____   ____ _| |_ _   _ _ __ ___ 
 | |_) / _ \ \ / / _` | __| | | | '__/ _ \
 |  _ <  __/\ V / (_| | |_| |_| | | |  __/
 |_| \_\___| \_/ \__,_|\__|\__,_|_|  \___|
 
-->

	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
	<header>
	  <div class="container">
	
		<%
			//display message if present
			if (request.getAttribute("message") != null){
				out.println("<p class=\"alert alert-danger\">" + request.getAttribute("message") + "</p>");
			}
		%>
		<h1>PUBHUB <small>Checkout</small></h1>
		
		<form action="CheckOut" type="POST">
		<table>
		<thead>
		<tr>
			<td>Title</td>
			<td>Price</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="userBook" items="${userBooks}">
			<tr>
				<td><input type="text" value="<c:out value="${userBooks.title}" readonly />"/> </td>
				<td><input type="text" value="<c:out value="${userBooks.Price}" />" readonly /></td>
			</tr>
				</c:forEach>
			<tr>
				<td><input type="submit" /></td>
			</tr>
		</tbody>
		</table>
		</form>

	  </div>
	</header>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />