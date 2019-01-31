
var domain = "";

var authToken;
var userRoles;
var userPermissions;
var models;

function showLogin() {
    $("#userAdministrationContent").hide();
    $("#loginContent").show();

    document.getElementById("loginContent").addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode == 13) {
            document.getElementById("btnLogin").click();
        }
    });
}

function showUserAdministratin() {
    $("#loginContent").hide();
    $("#userAdministrationContent").show();
}

$(function(){

    $("#permission_model_dialog").hide();

    authToken = localStorage["auth-token"];
    if (authToken) {
        loadCurrentUser();
    }else {
        showLogin();
    }
});

function loadCurrentUser() {
    $.ajax({
        type: "GET",
        beforeSend: function(request) {
            request.setRequestHeader("X-Auth-Token", authToken);
        },
        url: domain+"/api/user/current",
        processData: false,
        success: function(result) {
            if (result.role == 'ADMIN') {
                showUserAdministratin();
                loadData();
            }else {
                alert("You are not administrator.")
            }
        },
        complete: function(xhr, textStatus) {
            if (xhr.status != 200) {
                localStorage.removeItem("auth-token");
                showLogin();
            }
        }
    });
}

function createTable() {
    $("#user_table").jsGrid({
        height: "90%",
        width: "100%",

        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
        pageLoading: true,
        autoload: true,

        controller: {
            loadData: function(filter) {
                var data = $.Deferred();
                $.ajax({
                    type: "GET",
                    contentType: "application/json",
                    beforeSend: function(request) {
                        request.setRequestHeader("X-Auth-Token", authToken);
                    },
                    url: domain+"/api/user/all",
                }).done(function(response){
                    data.resolve({
                        "data" : response,
                        "itemsCount" : response.length
                    });
                });
                return data.promise();
            },

            insertItem: function (item) {
                item.hash = Sha256.hash(item.hash);
                $.ajax({
                    type: "POST",
                    beforeSend: function(request) {
                        request.setRequestHeader("X-Auth-Token", authToken);
                        request.setRequestHeader('Content-type', 'application/json');
                    },
                    url: domain+"/api/user/insert" ,
                    data: JSON.stringify(item),
                    processData: false,
                    success: function(msg) {
                        createTable();
                    },
                    complete: function(xhr, textStatus) {
                        if (xhr.status != 200) {
                            alert(xhr.responseJSON.message);
                        }
                    }
                });
            },

            updateItem: function (item) {
                console.log(item);
                $.ajax({
                    type: "POST",
                    beforeSend: function(request) {
                        request.setRequestHeader("X-Auth-Token", authToken);
                        request.setRequestHeader('Content-type', 'application/json');
                    },
                    url: domain+"/api/user/update" ,
                    data: JSON.stringify(item),
                    processData: false,
                    success: function(msg) {
                        createTable();
                    },
                    complete: function(xhr, textStatus) {
                        if (xhr.status != 200) {
                            alert(xhr.responseJSON.message);
                        }
                    }
                });
            },

            deleteItem: function (item) {
                $.ajax({
                    type: "GET",
                    beforeSend: function(request) {
                        request.setRequestHeader("X-Auth-Token", authToken);
                    },
                    url: domain+"/api/user/delete?userID="+item.id,
                    processData: false,
                    success: function(msg) {
                        createTable();
                    },
                    complete: function(xhr, textStatus) {
                        if (xhr.status != 200) {
                            alert(xhr.responseJSON.message);
                        }
                    }
                });
            }
        },

        fields: [
            { name: "username", title: "Username", type: "text",editing: false, width: 40},
            { name: "firstName", title: "FirstName", type: "text", width: 40 },
            { name: "lastName", title: "LastName", type: "text", width: 40 },
            { name: "email", title: "Email", type: "text", width: 50 },
            { name: "role", title: "Role", width: 30, type: "select", items: userRoles, valueField: "name", textField: "name" },
            { name: "allowed", type: "checkbox", title: "Allowed", sorting: false, width: 20 },
            {
                name: "hash",
                title: "Password",
                align: "center",
                type: "text",
                width: 30,
                editing: false,
                itemTemplate: function(_, item) {
                    return $("<button>").attr("type", "button").text("New password")
                        .on("click", function () {
                            var new_password = prompt("Please enter your new password.", "");
                            if (new_password) {
                                if (new_password.length != 0) {
                                    item.hash = Sha256.hash(new_password);
                                }else {
                                    alert("New password can not be empty.");
                                }
                            }
                        });
                }
            },
            {
                name: "permissions",
                title: "Permissions",
                align: "center",
                width: 30,
                editing: false,
                itemTemplate: function(_, item) {
                    return $("<button>").attr("type", "button").text("Change permissions")
                        .on("click", function () {
                            showPermissionsDialog(item);
                        });
                }
            },
            { type: "control" },
        ]
    });
}

function showPermissionsDialog(client) {

    $("#models_dialog tbody").empty();

    var content = "";
    for (var index in models) {
        var model = models[index];
        var modelContainUser = model['customers'].filter(function(e) { return e.userID == client.id; });
        var availablePermissions = (modelContainUser.length > 0) ? modelContainUser[0]['permissions'] : [];

        var permissionsOptions = "";
        for (var permissionIndex in userPermissions) {
            var permission = userPermissions[permissionIndex]["name"];

            var isChecked = $.inArray(permission, availablePermissions);
            permissionsOptions += "<td><input "+(isChecked == -1 ? "" : "checked")+" type='checkbox' value="+permission+" name="+permission+">"+permission+"</td>";
        }
        content += ("<tr id="+model['id']+"><td>"+model['name']+"</td>" + permissionsOptions + "</tr>");
        content += "<button type='button'>Return</button>";
    }

    $("#models_dialog tbody").append(content);
    $("#models_dialog tfoot").empty();

    $("#models_dialog tfoot").append($("<button>").attr("type", "button").text("Save")
        .on("click", function () {
            var permissionsBatches = [];
            $('#models_dialog tbody tr').each(function() {
                var rows = $(this).find('td');
                permBatche = {
                    "modelID" : Number(this.id),
                    "name"    : rows[0].innerText,
                    "permissions" : []
                };
                $(this).find('input').each(function(){
                    var isChecked = this.checked;
                    var name = this.value;
                    if (this.checked) {
                        permBatche['permissions'].push(this.value);
                    }
                });
                permissionsBatches.push(permBatche);
            })
            savePermissions(client.id, permissionsBatches);
        })
    );



    $("#permission_model_dialog").dialog({
        autoOpen: false,
        width: 450,
        height: 300
    }).dialog("open");
};

function savePermissions(userID, permissionsBatches) {
    $.ajax({
        type: "POST",
        beforeSend: function(request) {
            request.setRequestHeader("X-Auth-Token", authToken);
            request.setRequestHeader('Content-type', 'application/json');
        },
        url: domain+"/api/user/changeModelPermission?userID="+userID,
        processData: false,
        data: JSON.stringify(permissionsBatches),
        success: function(result) {
            console.log(result);
            $('#permission_model_dialog').dialog('close')
            loadModels();
        },
        complete: function(xhr, textStatus) {
            if (xhr.status != 200) {
                alert(xhr.responseJSON.message);
            }
        }
    });
}

function loadEnums() {
    $.ajax({
        type: "GET",
        beforeSend: function(request) {
            request.setRequestHeader("X-Auth-Token", authToken);
        },
        url: domain+"/api/enum/user",
        processData: false,
        success: function(result) {
            userRoles = JSON.parse(result["role"]);
            userPermissions = JSON.parse(result["permission"]);
            createTable();
        },
        complete: function(xhr, textStatus) {
            if (xhr.status != 200) {
                alert(xhr.responseJSON.message);
            }
        }
    });
}

function loadModels() {
    $.ajax({
        type: "GET",
        beforeSend: function(request) {
            request.setRequestHeader("X-Auth-Token", authToken);
        },
        url: domain+"/api/model/all",
        processData: false,
        success: function(result) {
            models = result;
        },
        complete: function(xhr, textStatus) {
            if (xhr.status != 200) {
                alert(xhr.responseJSON.message);
            }
        }
    });
}

function loadData() {
    loadModels();
    loadEnums();
}

function login() {
    var username = $("#inputUsermane").val();
    var password = $("#inputPassword").val();
    var hash = Sha256.hash(password);
    var credentials = btoa(username + ":" + hash);

    $.ajax({
        type: "POST",
        beforeSend: function(request) {
            request.setRequestHeader("Base-auth", credentials);
        },
        url: domain+"/api/auth/apitoken/login",
        processData: false,
        success: function(result) {
            authToken = result["auth-token"];
            localStorage.setItem("auth-token", authToken);
            loadCurrentUser();
        },
        complete: function(xhr, textStatus) {
            if (xhr.status != 200) {
                alert(xhr.responseJSON.message);
            }
        }
    });
}
