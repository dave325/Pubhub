
<!-- Header -->
<jsp:include page="header.jsp" />

<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 	Just some stuff you need -->
<header>
	<div class="container">

		<c:choose>
			<c:when test="${not empty message }">
				<p class="alert ${messageClass}">${message }</p>
				<%
					session.setAttribute("message", null);
							session.setAttribute("messageClass", null);
				%>
			</c:when>
		</c:choose>

		<h1>
			PUBHUB <small>Book Tags -- ${currentTags}</small>
		</h1>
		<table
			class="table table-striped table-hover table-responsive pubhub-datatable">
			<thead>
				<tr>
					<td>ISBN-13:</td>
					<td>Title:</td>
					<td>Author:</td>
					<td>Price:</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tags" items="${tags}">
					<tr>
						<td><c:out value="${tags.isbn13}" /></td>
						<td><c:out value="${tags.title}" /></td>
						<td><c:out value="${tags.author}" /></td>
						<td><c:out value="${tags.price}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
</header>

<!-- Footer -->
<jsp:include page="footer.jsp" />
