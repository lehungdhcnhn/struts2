<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$('.price').each(function() {
	    	calculateSum();
		});
	});

	 function calculateSum() {
		var sum = 0;
	    $(".price").each(function() {
	     	var value = $(this).text();
	    	    // add only if the value is number
	    	if(!isNaN(value) && value.length != 0) {
	    	        sum += parseFloat(value);
	    	    }
	    });
		$('.result').html(sum);    
	 };
	
	$("#formExcel").submit();
	
	$("#formExcel").ajaxForm({
		url:"resultAction.action",
		dataType:"json",
		type:"POST",
		resetForm:"true",
		beforeSubmit: function(){
			var fileName=$("#fileUpload").val();
			var suffix =(fileName.substr(fileName.lastIndexOf(".")+1)).toUpperCase();
			if(!(suffix!="XLS"||suffix!="XLSX")){
				alert("not correct file");
				return false;
			}
		},
		success: function(){
				alert("complete");
				location.reload();
		},
		error: function(){
				alert("error");
		}	
	});
	
	function uplod() {
		var form = $('#uploadImgForm')[0];
		var data = new FormData(form);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : "fileUploadAction",
			data : data,
			cache : false,
			processData : false,
			contentType : false,
			success : function() {
				console.log("SUCCESS ");
			},
			error : function() {
				console.log("ERROR");
			}
		});
	}
	
	function exportExcelUserPdf() {
		window.location.href = "exportPdf.action";
	}

	function exportExcelUser() {
		window.location.href = "export.action";
	}
	
	function addNewRecord() {
		var uname = $("#name").val();
		var udeg = $("#deg").val();
		var uemail = $("#email").val();
		var upass = $("#pass").val();
		var usalary=$("#usalary").val();
		$.ajax({
			type : "POST",
			url : "registeruser.action",
			data : "uname=" + uname + "&udeg=" + udeg + "&uemail=" + uemail
					+ "&upass=" + upass+"&usala"+usalary,
			success : function(data) {
				var ht = data.msg;
				$("#resp").html(ht);
				location.reload();
			},
			error : function(data) {
				alert("Some error occured.");
			}
		})
	}
	
	function report() {
		$.ajax({
				type : "GET",
				url : "report.action",
				success : function(result) {
					var tblData = "";
					$.each(result.listEmp,function() {
						tblData += "<tr><td class='center'>"
								+ this.uname
								+ "</td>"
								+ "<td class='center'>"
								+ this.uemail
								+ "</td>"
								+ "<td class='center'>"
								+ this.upass
 								+ "</td>"
								+ "<td class='center'>"
								+ this.udeg
								+ "</td>"
								+ "<td class='center price'>"
								+this.usalary
								+ "</td>"
								+ "<td class='center'>"
								+ "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Update</button>"
								+ "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>"
								+ "</td></tr>";
						});
						$("#tbody").html(tblData);
					},
					error : function(result) {
						alert("Some error occured.");
					}
			});
		
	};
	
		
	

	// function for fecthing old information into the form
	function fetchOldRecord(that) {
		$("#uname").val($(that).parent().prev().prev().prev().prev().text());
		$("#uemail").val($(that).parent().prev().prev().prev().text());
		$("#upass").val("");
		$("#udeg").val($(that).parent().prev().text());
		$("#usalary").val($(that).parent.prev().prev().prev().prev().text());
		$("#hiddenuemail").val($(that).parent().prev().prev().prev().text());
	}

	// function for updating new information into database
	function updateNewRecord() {
		$.ajax({
			type : "POST",
			url : "updateuser.action",
			data : "uname=" + $("#uname").val() + "&uemail="
					+ $("#uemail").val() + "&upass=" + $("#upass").val()
					+ "&udeg=" + $("#udeg").val() + "&hiddenuemail="
					+ $("#hiddenuemail").val()+$("#usalary").val(),
			success : function(result) {

				location.reload();
			},
			error : function(result) {
				alert("Some error occured.");
			}
		});
	}

	// function for deleting user information from database
	function deleteUser(that) {
		$.ajax({
			type : "POST",
			url : "deleteuser.action",
			data : "uemail=" + $(that).parent().prev().prev().prev().text(),
			success : function(data) {
				if (data.msg === "Delete Successful") {
					alert(data.msg)
					$(that).closest('tr').remove();
					location.reload();
				} else {
					alert(data.msg)
				}
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
	}
	
</script>
</head>
<body onload="report();">
	<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header" style="float: right;">
			<a class="navbar-brand" href="/">CRUD</a>
		</div>
		<ul class="nav navbar-nav">

			<li><a href="report.jsp">Report</a></li>
		</ul>
	</div>
	</nav>
	<div class="container">
		<button type="button" onclick="registerUser();"
			class="btn btn-success" style="margin-bottom: 20px"
			data-toggle='modal' data-target='#addModal'>Add user</button>
			<button type="button" style="float: right;"
			onclick="exportExcelUserPdf();" class="btn btn-success"
			style="margin-bottom: 20px">Export pdf</button>
		<button type="button" style="float: right;"
			onclick="exportExcelUser();" class="btn btn-success"
			style="margin-bottom: 20px">Export excel</button>
		    <form id="formExcel" enctype="multipart/form-data" action="resultAction"   method="post">
		<button type="submit" style="float:right;" 
			class="btn btn-success" style="margin-bottom: 20px">Import</button>
			<input type="file" id="fileUpload" name="fileUpload" class="form-control col-sm-3" style="width: 300px; float: right; margin-bottom:40px">
		</form>
 	 
		<%-- <s:form id ="formExcel" action="resultAction" namespace="/" method="POST"
			enctype="multipart/form-data">

			<s:file name="fileUpload" cssClass="form-control col-sm-3" cssStyle="width: 300px; float: right;" id="fileUpload" label="Select a File to upload" size="40" />

			<s:submit value="submit" cssClass="btn btn-success" cssStyle="float:right;margin-bottom: 20px" name="submit" />

		</s:form> --%>
		
		<table class="table table-borderless">
			<thead>
				<tr>
					<th style="text-align: center;" scope="col">Name</th>
					<th style="text-align: center;" scope="col">Email</th>
					<th style="text-align: center;" scope="col">Password</th>
					<th style="text-align: center;" scope="col">Designation</th>
					<th style="text-align: center;" scope="col">Salary</th>
					<th style="text-align: center;" scope="col">Action</th>	
				</tr>
				<tr>
					<th style="text-align: center;" scope="col"></th>
					<th style="text-align: center;" scope="col"></th>
					<th style="text-align: center;" scope="col"></th>
					<th style="text-align: center;" scope="col">ToTal: </th>
					<th style="text-align: center;" scope="col" class="result"></th>
					<th style="text-align: center;" scope="col"></th>	
				</tr>
				
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
	</div>
	<div class="container" id="updateBlock">
		<div class="modal fade" id="updateModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Update New Information</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="uname" id="uname"
										class="form-control input-sm" placeholder="Full Name">
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="udeg" id="udeg"
										class="form-control input-sm" placeholder="Designation">
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="text" name="uemail" id="uemail"
								class="form-control input-sm" placeholder="Email"> <input
								type="hidden" name="hiddenuemail" id="hiddenuemail">
						</div>
						<div class="form-group">
							<input type="password" name="upass" id="upass"
								class="form-control input-sm" placeholder="Password">
						</div>
						<div class="form-group">
							<input type="text" name="usalary" id="usalary"
								class="form-control input-sm" placeholder="Salary">
						</div>
						<button onclick="updateNewRecord();"
							class="btn btn-info btn-block">Update</button>
						<div id="resp" class="text-center" style="margin-top: 13px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container" id="addBlock">
		<div class="modal fade" id="addModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add New Information</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="name" id="name"
										class="form-control input-sm" placeholder="Full Name">
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="deg" id="deg"
										class="form-control input-sm" placeholder="Designation">
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="text" name="email" id="email"
								class="form-control input-sm" placeholder="Email"> <input
								type="hidden" name="hiddenuemail" id="hiddenuemail">
						</div>
						<div class="form-group">
							<input type="password" name="pass" id="pass"
								class="form-control input-sm" placeholder="Password">
						</div>
						<div class="form-group">
							<input type="text" name="usalary" id="usalary"
								class="form-control input-sm" placeholder="Salary">
						</div>
						<button onclick="addNewRecord();" class="btn btn-info btn-block">Add</button>
						<div id="resp" class="text-center" style="margin-top: 13px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>