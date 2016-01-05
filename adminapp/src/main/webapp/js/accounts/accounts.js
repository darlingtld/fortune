angular.module('AdminApp').
    controller('accountsController', function ($rootScope) {
        $rootScope.menu = 2;

        //TODO: validate null;  validate canDelete,canEnable,canDisable,canAdd,canSetCredit from backend
        (function ($) {
            $(function () {
                var ROOT = "-1", userDeleteMap = {};

                var initState = function () {
                    $(".first_level").empty();
                    showPGroupLevel(ROOT, ".first_level");
                    $("#delete_user").hide();
                    $("#add_user").hide();
                };

                // 展开某层pgroup
                var showPGroupLevel = function (pgroupId, selector) {
                    // 取当前用户作为管理员的pgroup
                    if (pgroupId == ROOT) {
                        $.get("pgroup/admin_pgroup/" + sessionStorage["username"], function (data) {
                            var html = '<li><p class="pgroup" data-id="' + data.id + '">' + data.name + '</p></li>';
                            $(html).appendTo($(selector));
                            $(selector).show();
                            $("#account_tree .pgroup:eq(0)").click();
                            // 并且，取出user列表
                            $.get("pgroup/allusers/" + data.id, function (data) {
                                if (data && data.length > 0) {
                                    console.log(data);
                                    var html = "";
                                    for (var i = 0; i < data.length; i++) {
                                        var user = data[i], pgroupList = user.pGroupList;
                                        html += "<tr><td>" + user.username + "</td><td class='pgroups_cell'>" + pgroupList[0].name + "</td><td class='" + user.status.toLowerCase() + "'>" +
                                            (user.status == "ENABLED" ? "已启用" : "已禁用") + "</td><td>" +
                                            user.creditAccount + "</td><td>" + user.usedCreditAccount + "</td>";
                                        if (user.canDelete) {
                                            userDeleteMap[user.id] = true;
                                            html += (user.status == "ENABLED" ? "<td><a href='javascript:;' class='red_btn disable_operate' data-id='" + user.id + "'>禁用</a>" : "<td><a href='javascript:;' class='btn enable_operate' data-id='" + user.id + "'>启用</a>") +
                                                "<a href='javascript:;' class='red_btn delete_operate' data-id='" + user.id + "'>删除</a>" +
                                                "<input type='text' style='float:left;margin-left:5px;width:100px;' value='" + user.creditAccount + "'/><a href='javascript:;' class='btn credit_setting' data-id='" + user.id + "'>设置额度</a></td></tr>";
                                        }
                                        else {
                                            html += "<td>--</td></tr>";
                                        }

                                    }
                                    $(".content table tbody").html(html);
                                }
                            });
                        });
                    }
                    // 取下一层pgroup和user
                    else {
                        $.get("pgroup/pgroups/" + pgroupId, function (data) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                var pgroup = data[i];
                                html += '<li><p class="pgroup" data-id="' + pgroup.id + '">' + pgroup.name + '</p></li>';
                            }
                            $(html).appendTo($(selector));
                            $(selector).show();
                        });
                        $.get("pgroup/users/" + pgroupId, function (data) {
                            if (data && data.length > 0) {
                                var html = "";
                                for (var i = 0; i < data.length; i++) {
                                    var user = data[i];
                                    html += '<li><p class="user ' + user.status.toLowerCase() + '" data-id="' + user.id + '">' + user.username + '</p></li>';
                                }
                                $(html).appendTo($(selector));
                                $(selector).show();
                            }
                        });
                    }
                };

                // 初始时候显示第一层
                showPGroupLevel(ROOT, ".first_level");

                $("body").on("click", "#account_tree .pgroup", function () {
                    $("#delete_user").hide();
                    if (!$(this).hasClass("clicked")) {
                        var node = $("<ul></ul>").insertAfter($(this));
                        showPGroupLevel($(this).attr("data-id"), node);
                        $(this).addClass("clicked"); //用于标记已经取过远程数据的pgroup
                    }
                    if ($(this).hasClass("selected")) {
                        $(this).next("ul").hide();
                        $(this).removeClass("selected");
                        $("#add_user").hide();
                        return;
                    }
                    else {
                        $(this).next("ul").show();
                        $("#account_tree p").removeClass("selected");
                        $(this).addClass("selected");
                        $("#add_user").show();
                        // 判断是否可以显示操作按钮
                        $.get("pgroup/can_delete/" + $(this).attr("data-id") + "/" + sessionStorage["username"], function (data) {
                            if (data) {
                                $("#delete_user").show();
                            }
                        });
                    }
                });

                $("body").on("click", "#account_tree .user", function () {
                    $("#delete_user").hide();
                    $("#add_user").hide();
                    if (userDeleteMap[$(this).attr("data-id")]) {
                        $("#delete_user").show();
                    }
                    $("#account_tree p").removeClass("selected");
                    $(this).addClass("selected");
                });

                $("body").on("click", "#add_pgroup", function () {
                    $(".dialog_title").html("增加新的代理商");
                    $(".dialog_body").html("<label for='pgroup_name_input'>代理商名称：</label><input type='text' id='pgroup_name_input'/><br/><br/><label for='admin_name_input'>管理员名称：</label><input type='text' id='admin_name_input'/><br/><br/><label for='admin_pwd_input'>密码：</label><input type='password' id='admin_pwd_input'/><br/><br/><label for='admin_repwd_input'>确认密码：</label><input type='password' id='admin_repwd_input'/>");
                    $(".dialog").show();
                });

                $("body").on("click", "#add_user", function () {
                    if ($("#account_tree .pgroup.selected").length > 0) {
                        $(".dialog_title").html("增加新的用户");
                        $(".dialog_body").html("<label for='user_name_input'>用户名称：</label><input type='text' id='user_name_input'/><br/><br/><label for='user_pwd_input'>密码：</label><input type='password' id='user_pwd_input'/><br/><br/><label for='user_repwd_input'>确认密码：</label><input type='password' id='user_repwd_input'/>");
                        $(".dialog").show();
                    }
                    else {
                        return false;
                    }
                });

                $("body").on("click", "#delete_user", function () {
                    if (!confirm("确认删除")) {
                        return;
                    }
                    if ($("#account_tree p.selected").length == 0) {
                        return false;
                    }
                    // 删除代理商
                    if ($("#account_tree p.selected").hasClass("pgroup")) {
                        $.post("pgroup/delete/pgroup/" + $("#account_tree p.selected").attr("data-id") + "/" + sessionStorage["username"], function () {
                            alert("删除成功");
                            initState();
                        });
                    }
                    // 删除用户
                    else {
                        $.post("pgroup/" + sessionStorage["pgroupid"] + "/delete/user/" + $("#account_tree p.selected").attr("data-id"), function () {
                            alert("删除成功");
                            initState();
                        });
                    }
                });

                $("body").on("click", ".delete_operate", function () {
                    if (!confirm("确认删除")) {
                        return;
                    }
                    $.post("pgroup/delete/" + sessionStorage["pgroupid"] + "/user/" + $(this).attr("data-id"), function () {
                        alert("删除成功");
                        initState();
                    });
                });

                $("body").on("click", ".enable_operate", function () {
                    if (!confirm("确认启用")) {
                        return;
                    }
                    $.post("pgroup/enable/user/" + $(this).attr("data-id"), function () {
                        alert("启用成功");
                        initState();
                    });
                });

                $("body").on("click", ".disable_operate", function () {
                    if (!confirm("确认禁用")) {
                        return;
                    }
                    $.post("pgroup/disable/user/" + $(this).attr("data-id"), function () {
                        alert("禁用成功");
                        initState();
                    });
                });

                $("body").on("click", ".credit_setting", function () {
                    // 获取额度值
                    var credit_input = $(this).prev("input");
                    if (!/^\d+$/.test(credit_input.val())) {
                        credit_input.css("background", "#FFC1C1");
                    }
                    else {
                        $.post("pgroup/credit_setting/user/" + $(this).attr("data-id") + "/" + credit_input.val(), function () {
                            alert("额度设置成功");
                            initState();
                        });
                    }
                });

                $("body").on("click", "#dialog_confirm", function () {
                    var parentId = $("#account_tree .pgroup:eq(0)").attr("data-id");
                    if ($("#pgroup_name_input").length > 0) {
                        if ($("#admin_pwd_input").val() != $("#admin_repwd_input").val()) {
                            alert('密码不一致');
                            return;
                        }
                        // 插入代理商
                        var name = $("#pgroup_name_input").val(),
                            admin = {
                                username: $("#admin_name_input").val(),
                                password: $("#admin_pwd_input").val(),
                                roleList: ["GROUP_ADMIN"]
                            },
                            userList = [];
                        if (name.trim() == '') {
                            alert('名字为空');
                            return;
                        }
                        if (admin.password.trim() == '') {
                            alert('密码为空');
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "pgroup/add_pgroup",
                            contentType: "application/json",
                            data: JSON.stringify({
                                name: name,
                                parentPGroupID: parentId,
                                admin: admin,
                                userList: userList
                            }),
                            success: function () {
                                $(".dialog").hide();
                                initState();
                                alert("新增代理商成功！");
                            },
                            error: function () {
                                alert("代理商或管理员已存在，请重新输入");
                            }
                        });
                    }
                    else {
                        if ($("#user_pwd_input").val() != $("#user_repwd_input").val()) {
                            alert('密码不一致');
                            return;
                        }
                        if ($("#user_name_input").val().trim() == '') {
                            alert('名字为空');
                            return;
                        }
                        if ($("#user_pwd_input").val().trim() == '') {
                            alert('密码为空');
                            return;
                        }
                        // 插入用户
                        $.ajax({
                            type: "POST",
                            url: "pgroup/" + parentId + "/add_user",
                            contentType: "application/json",
                            data: JSON.stringify({
                                username: $("#user_name_input").val(),
                                password: $("#user_pwd_input").val(),
                                roleList: ["NORMAL_USER"]
                            }),
                            success: function () {
                                $(".dialog").hide();
                                initState();
                                alert("新增用户成功");
                            },
                            error: function () {
                                alert("新增用户失败");
                            }
                        });
                    }
                });

                $("body").on("click", "#dialog_cancel", function () {
                    $(".dialog").hide();
                });
            });
        })(jQuery);
    });