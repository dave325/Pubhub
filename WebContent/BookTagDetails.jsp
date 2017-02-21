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
	
		<h1>PUBHUB <small>Book Details </small></h1>
		<hr class="book-primary">
		
		<form action="ViewBookByTags" method="get" class="form-horizontal">
		  
		  <div class="form-group">
		    <label for="tags" class="col-sm-4 control-label">Tags</label>
		    <div class="col-sm-5">
		      <input type="text" class="form-control" id="tags" name="booktags" placeholder="Tags" required="required" value="" />
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-4 col-sm-1">
		      <button type="submit" class="btn btn-info">Find tags</button>
		    </div>
		  </div>
		</form>
		
	
	  </div>
	</header>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />