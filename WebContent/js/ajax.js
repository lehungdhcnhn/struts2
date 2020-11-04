$("#formExcel").submit();
$("#formExcel").ajaxForm(
			{
				url : "resultAction.action",
				dataType : "json",
				type : "POST",
				resetForm : "true",
				beforeSubmit : function() {
					var fileName = $("#fileUpload").val();
					var suffix = (fileName
							.substr(fileName.lastIndexOf(".") + 1))
							.toUpperCase();
					if (!(suffix != "XLS" || suffix != "XLSX")) {
						alert("not correct file");
						return false;
					}
				},
				success : function() {
					alert("complete");
					location.reload();
				},
				error : function() {
					alert("error");
				}
			});

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
		var usalary = $("#salary").val();
		$.ajax({
			type : "POST",
			url : "registeruser.action",
			data : "uname=" + uname + "&udeg=" + udeg + "&uemail=" + uemail
					+ "&upass=" + upass + "&usalary=" + usalary,
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
		$
				.ajax({
					type : "GET",
					url : "report.action",
					success : function(result) {
						var tblData = "";
						$
								.each(
										result.listEmp,
										function() {
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
													+ "<td  class='center'>"
													+ this.usalary
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

	function updateNewRecord() {
		$.ajax({
			type : "POST",
			url : "updateuser.action",
			data : "uname=" + $("#uname").val() + "&uemail="
					+ $("#uemail").val() + "&upass=" + $("#upass").val()
					+ "&udeg=" + $("#udeg").val() + "&hiddenuemail="
					+ $("#hiddenuemail").val() + "&usalary="+$("#usalary").val(),
			success : function(result) {

				location.reload();
			},
			error : function(result) {
				alert("Some error occured.");
			}
		});
	}

function deleteUser(that) {
		$.ajax({
			type : "POST",
			url : "deleteuser.action",
			data : "uemail=" +$(that).parent().prev().prev().prev().prev().text(),
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

function fetchOldRecord(that) {
		$("#uname").val($(that).parent().prev().prev().prev().prev().prev().text());
		$("#udeg").val($(that).parent().prev().prev().text());
		$("#uemail").val($(that).parent().prev().prev().prev().prev().text());
		$("#upass").val("");	
		$("#usalary").val($(that).parent().prev().text());
		$("#hiddenuemail").val($(that).parent().prev().prev().prev().text());
}