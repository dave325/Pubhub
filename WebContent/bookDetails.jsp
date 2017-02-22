
<!-- Header -->
<jsp:include page="header.jsp" />

<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 	Just some stuff you need -->
<header>
	<div class="container">
	<c:out value="${activeAccount} "/>
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
			PUBHUB <small>Book Details - ${book.isbn13 }</small>
		</h1>
		<hr class="book-primary">

		<form action="UpdateBook" method="post" class="form-horizontal">

			<input type="hidden" class="form-control" id="isbn13" name="isbn13"
				required="required" value="${book.isbn13 }" />

			<div class="form-group">
				<label for="title" class="col-sm-4 control-label">Title</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="title" name="title"
						placeholder="Title" required="required" value="${book.title }" />
				</div>
			</div>
			<div class="form-group">
				<label for="author" class="col-sm-4 control-label">Author</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="author" name="author"
						placeholder="Author" required="required" value="${book.author }" />
				</div>
			</div>
			<div class="form-group">
				<label for="price" class="col-sm-4 control-label">Price</label>
				<div class="col-sm-5">
					<input type="number" step="0.01" class="form-control" id="price"
						name="price" placeholder="Price" required="required"
						value="${book.price }" />
				</div>
			</div>
			<c:choose>
				<c:when test="${not empty activeAccount }">
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-1">
							<button type="submit" class="btn btn-info">Update</button>
						</div>
					</div>
		</form>
		<form action="AddToCart" method="post" class="form-horizontal">
			<input type="hidden" class="form-control" id="isbn13" name="isbn13"
				required="required" value="${book.isbn13 }" />
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-1">
					<button type="submit" class="btn btn-info">Add to Cart</button>
				</div>
			</div>
		</form>
		</c:when>
		<c:otherwise>
				<div class="form-group">
					<div class="col-sm-offset-4  col-sm-4">
						<a href="LoginPage" class="btn btn-info">Login to update or
							buy!</a>
					</div>
				</div>
		</c:otherwise>
		</c:choose>
		<p>Tags for the book are: ${bookTags.tags}</p>
		<form action="UpdateBookTags" method="post" class="form-horizontal">
			<input type="hidden" class="form-control" id="isbn13" name="isbn13"
				required="required" value="${book.isbn13 }" />
			<div class="form-group">
				<label for="tags" class="col-sm-4 control-label">Tags
					(Seperated by a comma)</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="booktags"
						name="booktags" placeholder="Tags" required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-1">
					<button type="submit" class="btn btn-info">Add</button>
				</div>
			</div>
		</form>
		<form action="DeleteBookTags" method="post" class="form-horizontal">

			<input type="hidden" class="form-control" id="isbn13" name="isbn13"
				required="required" value="${book.isbn13 }" />

			<div class="form-group">
				<label for="tags" class="col-sm-4 control-label">Tags to
					Delete (Seperated by a comma)</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="tags" name="booktags"
						placeholder="Tags" required="required" value="${tags.tags }" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-1">
					<button type="submit" class="btn btn-info">Delete tags</button>
				</div>
			</div>
		</form>

	</div>
</header>

<!-- Footer -->
<jsp:include page="footer.jsp" />
