var Config = {};
Config.isDropDown = true;
Config.isSlide = true;
Config.dropDownWithSlide = true;
var jswin;
function initJsWin() {
	jswin = window.top.footFrame.main
}
initJsWin();
var APP_TYPE = "0";
var MG_TYPE = "1";
var MODULE_TYPE = "2";
var RES_TYPE = "3";
if (typeof (Tool) == "undefined") {
	Tool = {}
}
if (typeof (Tool.util) == "undefined") {
	Tool.util = {}
}
Tool.util.showTitleMsg = function(e, d, f) {
	if (e) {
		if (f) {
			e.innerHTML = f
		}
		if (d === true) {
			e.className = "success-msg-div"
		} else {
			if (d == "warning") {
				e.className = "waring-msg-div"
			} else {
				e.className = "error-msg-div"
			}
		}
		e.style.display = "block"
	}
};
fmArray = new Array();
fmsize = 0;
fmcount = 0;
var menuGrpsOfApp;
var mglast;
var appHomePage = null;
if (0 <= _fontSize && _fontSize <= 6) {
	tabConfig.fontSize = TabUtil.szs[_fontSize]
} else {
	tabConfig.fontSize = TabUtil.szs[0]
}
tabConfig.isTab = tabEnable == 1 ? true : false;
tabConfig.tabPosition = (_tabPosition === 0) ? "bottom" : "top";
var currentLang = "";
var fmgcount = 0;
var fmcount = 0;
var fmgsize = 0;
var fmsize = 0;
var fmgArray = new Array();
var fmArray = new Array();
String.prototype.merge = function(list) {
	if (!list) {
		return this
	}
	var st = this;
	for (var i = 0; i < list.length; i++) {
		var d = eval("/\\{" + i + "\\}/g");
		var index = st.indexOf(d);
		st = st.replace(d, list[i])
	}
	return st
};
function openHomePage() {
	var location = null;
	try {
		location = getCookie("fLocation")
	} catch (e) {
	}
	try {
		clearCookie("fLocation")
	} catch (e) {
	}
	if (!isNull(location)) {
		location = eval("(" + location + ")");
		if (location && location.name) {
			openMenu(location.name, null, location.type)
		} else {
			doOpen(location)
		}
	} else {
		var change = false;
		if (!isNull(appHomePage)) {
			appHomePage = getTokenUrl(appHomePage);
			if (tabEnable == 1 && homePageUseTab == 0) {
				tabConfig.isTab = false;
				change = true
			}
			CurrentMenu.isAppHomepage = true;
			openTab(appHomePage, Global.appName, "homepage")
		}
		if (change) {
			tabConfig.isTab = true
		}
		getAutoActiveMenus();
		if (!isNull(Global.autoActiveMgid)) {
			var el = document.getElementById("menugroup_"
					+ Global.autoActiveMgid);
			if (el) {
				el.onclick();
				var menuEl = document.getElementById("menu_"
						+ Global.autoActiveMenuid);
				if (menuEl) {
					menuEl.onclick()
				}
			}
		}
	}
	if (Global.toolMenu.whereami) {
		r1message.init();
		r1message.display.show();
		drag(document.getElementById("whereAmITitle"))
	}
}
function reloadFunTree() {
	if (CurrentMenu.moduleId) {
		window.top.footFrame.left.setFunSrc(CurrentMenu.moduleId, true)
	} else {
		if (Global.autoActiveMenuid) {
			var b = getMenu(Global.autoActiveMenuid);
			if (b) {
				window.top.footFrame.left.setFunSrc(b.id, true)
			}
		}
	}
}
function isNull(b) {
	if (!b || typeof b == "undefine") {
		return true
	}
	if (b == "" || b == "null") {
		return true
	}
	return false
}
function getAutoActiveMenus() {
	Global.autoActiveMgid = null, Global.autoActiveMenuid = null;
	if (!isNull(Global.autoActiveMenu.mgID)
			&& getMenugroup(Global.autoActiveMenu.mgID)) {
		Global.autoActiveMgid = Global.autoActiveMenu.mgID;
		if (!isNull(Global.autoActiveMenu.menuID)
				&& getMenu(Global.autoActiveMenu.menuID)) {
			Global.autoActiveMenuid = Global.autoActiveMenu.menuID;
			return
		}
	}
	if (isNull(Global.autoActiveMgid)
			&& !isNull(Global.autoActiveMenu.moduleCode)) {
		var e = getMenu(Global.autoActiveMenu.moduleCode, 2);
		Global.autoActiveMenu.moduleCode = null
	}
	if (isNull(Global.autoActiveMgid)
			&& !isNull(Global.autoActiveMenu.callMuuid)) {
		var e = getMenu(Global.autoActiveMenu.callMuuid, 1);
		Global.autoActiveMenu.callMuuid = null
	}
	if (e) {
		Global.autoActiveMenuid = e.menuid;
		var d = getMenugroupByMenu(e);
		if (d) {
			Global.autoActiveMgid = d.id
		}
	}
	if (!isNull(Global.autoActiveMgid)) {
		return
	}
	if (!isNull(Global.custMenu.mgID) && getMenugroup(Global.custMenu.mgID)) {
		Global.autoActiveMgid = Global.custMenu.mgID;
		if (!isNull(Global.custMenu.menuID) && getMenu(Global.custMenu.menuID)) {
			Global.autoActiveMenuid = Global.custMenu.menuID;
			return
		}
	}
	if (Global.autoOpenFirstMenu == "1") {
		var d = ronePortalJson.menugroup[0];
		if (typeof d != "undefined" && d) {
			Global.autoActiveMgid = d.id
		}
		var f = getMenuOfGroup(d);
		if (f && f.length > 0) {
			Global.autoActiveMenuid = f[0].menuid
		}
	}
}
function getMenuOfGroup(h) {
	var g = new Array();
	var f = h.menuids;
	for (var i = 0; i < f.length; i++) {
		var j = f[i].id;
		var h = getMenu(j);
		g.push(h)
	}
	return g
}
function getMenugroupOfApp(j) {
	var g = new Array();
	var l = j.mgids;
	for (var h = 0; h < l.length; h++) {
		var k = l[h].id;
		var i = getMenugroup(k);
		g.push(i)
	}
	return g
}
function getAppOfSystem(h) {
	var g = new Array();
	var i = h.appids;
	for (var j = 0; j < i.length; j++) {
		var k = i[j].id;
		var l = getApplication(k);
		g.push(l)
	}
	return g
}
function getAppOfChannel(h) {
	var g = new Array();
	var i = h.appids;
	for (var j = 0; j < i.length; j++) {
		var k = i[j].id;
		var l = getApplication(k);
		g.push(l)
	}
	return g
}
function getObjectByID(e, f) {
	if (f) {
		for (var d = 0; d < e.length; d++) {
			if (e[d].id == f) {
				return e[d];
				break
			}
		}
	}
	return e
}
function getChannel(c) {
	var d = ronePortalJson.channel;
	return getObjectByID(d, c)
}
function addParam2Url(d, e, f) {
	if (isNull(d)) {
		return
	}
	d += (d.indexOf("?") > -1) ? "&" : "?";
	d += e + "=" + f;
	return d
}
function getSystem(c) {
	var d = ronePortalJson.system;
	if (!c) {
		return d
	}
	return getObjectByID(d, c)
}
function getApplication(c) {
	var d = ronePortalJson.application;
	if (!c) {
		return d
	}
	return getObjectByID(d, c)
}
function getMenugroup(e) {
	var d = ronePortalJson.menugroup;
	if (!e) {
		return d
	}
	var f = getObjectByID(d, e);
	if (f.navurl == undefined) {
		f.navurl = ""
	}
	if (f.oaentry == undefined) {
		f.oaentry = ""
	}
	return f
}
function getMenugroupByMenu(j) {
	if (!j) {
		return null
	}
	var i = ronePortalJson.menugroup;
	if (!i) {
		return
	}
	for (var g = 0; g < i.length; g++) {
		var f = i[g].menuids;
		for (var h = 0; h < f.length; h++) {
			if (j.menuid == f[h].id) {
				return i[g]
			}
		}
	}
	return null
}
function getMenu(j, g) {
	var f = ronePortalJson.menu;
	var i;
	if (j) {
		g = typeof g == "undefined" ? 0 : g;
		for (var h = 0; h < f.length; h++) {
			switch (g) {
			case 0:
				i = f[h].menuid;
				break;
			case 1:
				i = f[h].id;
				break;
			case 2:
				i = f[h].code;
				break;
			case 3:
				i = f[h].name;
				break
			}
			if (i && i == j) {
				if (typeof (f[h].navurl) == "undefined") {
					f[h].navurl = ""
				}
				if (typeof (f[h].src) == "undefined") {
					f[h].src = ""
				}
				return f[h];
				break
			}
		}
	}
	return null
}
function getTokenUrl(d, e) {
	if (typeof e == "undefined") {
		d = (d.substring(0.4) != "http" && "" != Global.security.domain) ? Global.security.domain
				+ d
				: d
	}
	if (!Global.security.carry && typeof e == "undefined") {
		return d
	}
	if (Global.isSwitched || !Global.tokenURL) {
		var f = top.Ext.lib.Ajax.getConnectionObject().conn;
		f.open("GET", Global.path + "/FunctionTreeServlet?flag=getTokenURL",
				false);
		f.send(null);
		if (f.responseText == "-1") {
			timeout("RP003");
			return
		} else {
			Global.tokenURL = "result=login&LTPAToken=" + f.responseText;
			Global.isSwitched = false
		}
	}
	if (d != null && d != "" && d != "null" && d.indexOf("LTPAToken") == -1) {
		d = d.substring(d.length - 1, d.length) == "/" ? d.substring(0,
				d.length - 1) : d;
		d += d.indexOf("?") != -1 ? "&" : "?";
		d += Global.tokenURL
	}
	return d
}
var navBar;
function showLoaderMsg(h, e, f) {
	var g = document.getElementById("loader-div");
	g.style.display = "block";
	g.innerHTML = f;
	if (h && e) {
		g.style.top = document.body.scrollTop + e + 18 + "px";
		g.style.left = document.body.scrollLeft + h + 10 + "px"
	}
}
function hideLoaderMsg() {
	var b = document.getElementById("loader-div");
	b.style.display = "none"
}
Loader.failTimes = 0;
function showWhoOnline(g) {
	if (!Tool.whoOnline) {
		g = g || window.event;
		var i = null, j = null;
		if (g) {
			i = g.clientX;
			j = g.clientY
		}
		if (Loader.failTimes > 50) {
			msg = I18N.frame_load_fail;
			showLoaderMsg(i, j, msg);
			Loader.failTimes = 0;
			return
		} else {
			var h = "......".substring(0, Loader.failTimes % 6) + "      ";
			h = h.substring(0, 6);
			msg = "<pre>" + I18N.frame_load_loading + "<b>" + h + "</b></pre>";
			showLoaderMsg(i, j, msg)
		}
		Loader.load("ext-tool-js", Global.path + "/includes/ext-min/ext-tool."
				+ Global.jsCtrl);
		if (Ext.QuickTips) {
			Loader.load("tool-js", Global.path + "/includes/rone-ext/tool."
					+ Global.jsCtrl)
		}
		Loader.load("ToolMenuManager-js", Global.path
				+ "/dwr/interface/ToolMenuManager.js");
		Loader.load("dwr-engine-js", Global.path + "/dwr/engine.js");
		Loader.load("check-css", Global.consoleSkinPath + "/rone/style/check."
				+ Global.cssCtrl)
	}
	try {
		Tool.whoOnline.show();
		Loader.failTimes = 0;
		hideLoaderMsg()
	} catch (e) {
		Loader.failTimes++;
		setTimeout("showWhoOnline();", 300)
	}
}
var changeApp = {
	appuuid : null,
	appname : null
};
function changePassword(f, g, h) {
	changeApp.appuuid = typeof g != "undefined" ? g : changeApp.appuuid;
	changeApp.appname = typeof h != "undefined" ? h : changeApp.appname;
	if (!Tool.changePasswordDlg) {
		loadJsNeed()
	}
	try {
		Tool.changePasswordDlg.show();
		Loader.failTimes = 0;
		hideLoaderMsg()
	} catch (e) {
		Loader.failTimes++;
		setTimeout("changePassword();", 300)
	}
}
function loadJsNeed() {
	var d = document.getElementById("header").clientWidth / 2;
	var e = document.getElementById("header").clientHeight / 2 - 30;
	if (Loader.failTimes > 60) {
		msg = "无法加载,请检查网络";
		showLoaderMsg(d, e, msg);
		Loader.failTimes = 0;
		return
	} else {
		var f = "......".substring(0, Loader.failTimes % 6) + "      ";
		f = f.substring(0, 6);
		msg = "<pre>正在加载<b>" + f + "</b></pre>";
		showLoaderMsg(d, e, msg)
	}
	Loader.load("check-css", Global.consoleSkinPath + "/rone/style/check."
			+ Global.cssCtrl);
	Loader.load("ext-tool-js", Global.path + "/includes/ext-min/ext-tool."
			+ Global.jsCtrl);
	if (Ext.QuickTips) {
		Loader.load("tool-js", Global.path + "/includes/rone-ext/tool."
				+ Global.jsCtrl)
	}
}
function showLicense() {
	loadJsNeed();
	try {
		Tool.license.show();
		Loader.failTimes = 0;
		hideLoaderMsg()
	} catch (b) {
		Loader.failTimes++;
		setTimeout("showLicense();", 300)
	}
}
var roneHelpUrl = null;
var roneHelpTitle = null;
function showHelp(d, e) {
	loadJsNeed();
	try {
		if (d != null && typeof d != "undefined") {
			roneHelpUrl = d
		}
		if (e != null && typeof e != "undefined") {
			roneHelpTitle = e
		}
		e = e ? e : roneHelpTitle;
		d = d ? d : roneHelpUrl;
		Tool.help.show(d, e);
		Loader.failTimes = 0;
		hideLoaderMsg()
	} catch (f) {
		Loader.failTimes++;
		setTimeout("showHelp();", 300)
	}
}
function toolMenuAction(g, h) {
	if (g.id == "change") {
		changePassword(h, null, null)
	} else {
		if (g.id == "favorites") {
			var j = window.document.title;
			var i = window.location.href;
			if (i.indexOf("?") != -1) {
				i = i.substring(0, i.indexOf("?"))
			}
			if (window.sidebar) {
				window.sidebar.addPanel(j, i, "")
			} else {
				if (document.all) {
					window.external.AddFavorite(i, j)
				} else {
					if (window.opera && window.print) {
						return true
					}
				}
			}
		} else {
			if (g.id == "homepage") {
				swithLocation = "#"
			} else {
				if (g.id == "firstpage") {
					swithLocation = "#"
				} else {
					if (g.id == "defaultpage") {
						swithLocation = "#"
					} else {
						if (g.id == "online") {
							showWhoOnline(h)
						} else {
							if (g.id == "aboutus") {
								showLicense()
							} else {
								if (g.id == "node") {
									window.top
											.openTab(
													Global.path
															+ "/RoDomainServlet?flag=toList",
													I18N.frame_toolset_visit_other_node)
								} else {
									if (g.id == "pulish") {
										window.top
												.openTab(
														Global.path
																+ "/PublishServlet?flag=publish",
														I18N.portal_frame_toolbar_pulish)
									} else {
										if (g.id == "custom") {
											window.top
													.openTab(
															Global.path
																	+ "/CustomizeServlet",
															I18N.portal_frame_toolbar_custom)
										} else {
											if (g.id == "logout") {
											} else {
												if (g.id == "help") {
													var f = getApplication(CurrentApp.appID);
													window.open(f.helpurl)
												} else {
													if (g.id == "portalhelp") {
														window
																.open(Global.path
																		+ "/help/portalHelp.html")
													} else {
														if (g.id == "psgishelp") {
															window
																	.open(Global.path
																			+ "/help/psgisHelp.html")
														} else {
															if (g.id == "whereami") {
																r1message.display
																		.show()
															} else {
																if (g.id == "personinfo") {
																	window.top
																			.openTab(
																					Global.path
																							+ "/PersonInfoChangeServlet",
																					"个人资料维护")
																} else {
																	if (g.id == "setDefault") {
																		setDefaultPage(true)
																	} else {
																		if (g.id == "resetDefault") {
																			setDefaultPage(false)
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
function switchLocation(b) {
	window.footFrame.mainFrame.location = b
}
function setDefaultPage(g) {
	var i = new Array();
	i.push(CurrentApp.appName);
	var l = CurrentMenu.menuName ? CurrentMenu.menuName : CurrentMenu.mgName;
	if (l) {
		i.push(l)
	}
	var j = l ? I18N.frame_default_url : I18N.frame_default_app;
	j = g ? j : I18N.frame_default_cancel;
	var k = g ? "set" : "reset";
	j = j.merge(i);
	var m = "";
	if (g && l) {
		m += CurrentApp.appGrpID ? CurrentApp.appGrpID : "-1";
		m += "*";
		m += CurrentApp.appID ? CurrentApp.appID : "-1";
		m += "*";
		m += CurrentMenu.mgid ? CurrentMenu.mgid : "-1";
		m += "*";
		m += CurrentMenu.menuId ? CurrentMenu.menuId : "-1"
	}
	var h = g ? I18N.portal_frame_toolbar_homepage
			: I18N.portal_frame_toolbar_default;
	if (confirm(j)) {
		Ext.Ajax.request({
			url : Global.path + "/CustomizeServlet?flag=defaultpage",
			params : {
				sysid : CurrentApp.sysID,
				appuuid : CurrentApp.appID,
				defaultUrl : m,
				setType : k
			},
			success : function(a) {
				if (a.responseText && a.responseText == "1") {
					alert(h + I18N.frame_default_succeed);
					return
				}
				alert(h + I18N.frame_default_fail + a.responseText)
			},
			failure : function(a) {
				alert(h + I18N.frame_default_fail + a.responseText)
			}
		})
	}
}
function swithLocation(b) {
	window.footFrame.right.location = b
}
function setNavProperty(j, g, l, h, k, m, i) {
	navBar.setNavProperty(j, g, l, h, k, m, i)
}
function setSearchEntry(b) {
	window.footFrame.left.setSearchEntry(b)
}
function removeArea(g) {
	if (g == "menu") {
		removeAreaItem("menu")
	}
	if (g == "menugroup") {
		removeAreaItem("menu");
		removeAreaItem("menugroup")
	}
	if (g == "channel") {
		removeAreaItem("menu");
		removeAreaItem("menugroup");
		removeAreaItem("channel");
		scroller.show("mg-down", false);
		scroller.show("menu-down", false);
		CurrentMenu.init()
	}
	if (g == "system") {
		CurrentApp.init();
		removeAreaItem("menu");
		removeAreaItem("menugroup");
		removeAreaItem("channel");
		var e = Ext.get("systemitems").query("div");
		for (var h = 0; h < e.length; h++) {
			var f = new Ext.Element(e[h]);
			f.remove()
		}
	}
}
function removeAreaItem(g) {
	var e = Ext.get(g + "list").query("div");
	for (var h = 0; h < e.length; h++) {
		var f = new Ext.Element(e[h]);
		f.remove()
	}
	e = Ext.get(g + "list").query(".c" + g);
	if (e) {
		for (h = 0; h < e.length; h++) {
			f = new Ext.Element(e[h]);
			f.remove()
		}
	}
}
function switchSystem(j) {
	var h = Ext.get("subsystemchoide");
	var k = Ext.get("tool_subsystem");
	if (j) {
		var l = k.getXY();
		var i = k.getWidth();
		h.applyStyles("display:block");
		var g = h.getWidth();
		h.position("absolute", 100, l[0] + i / 2 - 80, l[1] + k.getHeight());
		Ext.get("switchSystem-point").focus()
	} else {
		h.applyStyles("display:none")
	}
}
function createSystem() {
	var l = Ext.get("systemitems");
	removeArea("system");
	var g = Ext.get("tool_subsystem");
	var i = getSystem();
	var j = getChannel();
	var k = null;
	if (i.length == 1) {
		g.applyStyles("display:none");
		switchSystem(false);
		k = i[0]
	} else {
		g.applyStyles("display:block");
		for (var m = 0; m < i.length; m++) {
			var o = {
				tag : "div",
				id : "system_" + i[m].id
			};
			if (i[m].current) {
				k = i[m];
				o.cls = "curitem";
				o.title = I18N.frame_system_currrent
			} else {
				o.cls = "item";
				o.title = I18N.frame_system_currrent;
				o.onClick = "roneSwitch('system'," + i[m].id + ")"
			}
			var n = l.createChild(o);
			n.insertHtml("afterBegin", i[m].name, false)
		}
		if (k == null) {
			k = i[0]
		}
	}
	createChannel(k)
}
function createChannel(q) {
	var i = null;
	var n = getChannel();
	var g = null;
	var n = getChannel();
	removeArea("channel");
	var j = n.length;
	var p = document.getElementById("channellist");
	if (j == 1) {
		g = n[0];
		var o = getAppOfChannel(g);
		if (o.length == 1) {
			i = o[0];
			p.style.display = Global.toolMenu.hideAppOnlyOne ? "none" : "block"
		} else {
			p.style.display = "block";
			for (var m = 0; m < o.length; m++) {
				if (o[m].current) {
					i = o[m];
					break
				}
			}
		}
		for (var l = 0; l < o.length; l++) {
			createChannelAreaItem(o[l], "app")
		}
	} else {
		p.style.display = "block";
		n.bubbleSort();
		for (var l = 0; l < n.length; l++) {
			var o = getAppOfChannel(n[l]);
			if (o.length == 1) {
				createChannelAreaItem(o[0], "app")
			} else {
				createChannelAreaItem(n[l], "channel")
			}
			if (n[l].current) {
				g = n[l]
			}
			for (var m = 0; m < o.length; m++) {
				if (o[m].current) {
					i = o[m];
					break
				}
			}
		}
	}
	CurrentApp.sysID = q.id;
	CurrentApp.sysName = q.name;
	CurrentApp.appGrpID = g.id;
	CurrentApp.appGrpName = g.name;
	CurrentApp.appID = i.id;
	CurrentApp.appName = i.name;
	if (q.priority == 0) {
		i.homepage = q.syshomepage;
		Global.appName = q.name
	}
	atvieApp(i);
	switchSystem(false)
}
function createChannelAreaItem(u, q) {
	if (q != "app") {
		var g = Ext.get("channellist");
		var t;
		var m = null;
		var n = null;
		var p = "";
		if (u.current) {
			defaultChannel = u;
			m = Global.skinpath + "/rone/images/frame/barrow.gif";
			p = I18N.frame_channel_current;
			n = "curchannel"
		} else {
			m = Global.skinpath + "/rone/images/frame/warrow.gif";
			n = "channel"
		}
		n += " channel_" + u.name;
		p += I18N.frame_channel_click;
		t = g.createChild({
			tag : "div",
			id : "channel_" + u.id,
			cls : n,
			title : p,
			onMouseOver : "arrowShower(true, " + u.id + ")",
			onMouseOut : "arrowShower(false, " + u.id + ")"
		});
		t.addClassOnOver("channel-over");
		var r = t.createChild({
			tag : "div",
			id : "channel_inner_" + u.id,
			onClick : "roneSwitch('channel','" + u.id + "')",
			cls : "channel_inner"
		});
		r.insertHtml("afterBegin", u.name, false);
		var o = t.createChild({
			tag : "div",
			id : "channel_arrow_div_" + u.id,
			cls : "arrowDiv",
			onClick : "activeApp(" + u.id + ")"
		});
		o.createChild({
			tag : "img",
			id : "channel_arrow_" + u.id,
			src : m,
			cls : "arrow"
		});
		t.addClassOnOver("channel-over")
	} else {
		if (typeof (isFilteredApp) == "function") {
			if (isFilteredApp(u)) {
				return
			}
		}
		var s = Ext.get("channellist");
		var n = u.current ? "curchannel" : "channel";
		n += " app_" + u.name;
		var m = null;
		var p = null;
		p = u.current ? I18N.frame_app_current : I18N.frame_app_switch;
		var l = {
			tag : "div",
			id : u.id,
			cls : n,
			title : p
		};
		l.onClick = "roneSwitch('app','" + u.id + "')";
		var t = s.createChild(l);
		t.insertHtml("afterBegin", u.name, false);
		t.applyStyles("padding-right:5px;")
	}
}
function atvieApp(b) {
	activeMenuGroup(b.id, b.homepage, b.helpurl)
}
function arrowShower(d, c) {
}
function roneSwitch(t, id, server, context) {
	scroller.reset();
	var params = {
		Way : "login",
		entrymode : "switch"
	};
	var url = Global.security.domain + Global.security.context + "/switch";
	var crossDomain = !(Global.security.domain == "");
	if (t == "system") {
		params.sysId = id
	} else {
		if (t == "channel") {
			params.sysId = CurrentApp.sysID;
			params.appgrpid = id
		} else {
			if (t == "app") {
				params.sysId = CurrentApp.sysID;
				params.appgrpid = CurrentApp.appGrpID;
				params.curAppuuid = id
			} else {
				if (t == "appSkinChange") {
					go(Global.path + "/login?sysId=" + CurrentApp.sysID
							+ "&appgrpid=" + CurrentApp.appGrpID
							+ "&curAppuuid=" + id
							+ "&Way=login&entrymode=switch&lang=" + currentLang);
					return
				} else {
					if (t == "idtype") {
						params.idtype = id
					} else {
						if (t == "node") {
							url = server + context;
							crossDomain = (server != "");
							params.entrymode = crossDomain ? "login" : "switch";
							url += crossDomain ? "/login" : "/switch";
							url = crossDomain ? getTokenUrl(url, true) : url
						}
					}
				}
			}
		}
	}
	
	Ext.get("mg-outer").mask(I18N.switchNow);
	Ext.Ajax
			.request({
				url : url,
				params : params,
				scriptTag : crossDomain,
				success : function(response) {
					Global.logout.destroyWhenLeft = false;
					if (!response || !response.responseText
							|| response.responseText == "") {
						return
					}
					if (response.responseText.indexOf("window.top") != -1) {
						eval(response.responseText);
						return
					}
					if (t == "node") {
						Global.security.domain = server;
						Global.security.context = context;
						window.top.footFrame.left.Fun.switchLoader()
					}
					if (t == "idtype") {
						document.getElementById("idtypeWitch_1").style.display = (id == 0) ? "block"
								: "none";
						document.getElementById("idtypeWitch_2").style.display = (id == 0) ? "none"
								: "block"
					}
					Global.isSwitched = true;
					ronePortalJson = eval("(" + response.responseText + ")");
					createSystem();
					Ext.get("mg-outer").unmask();
					openHomePage();
					Global.logout.destroyWhenLeft = true
				}
			})
}
function appMenuAction(b) {
	roneSwitch("app", b.id)
}
function activeApp(j) {
	var l = getChannel(parseInt(j));
	var h = getAppOfChannel(l);
	var g = Ext.get("channel_" + j);
	appMenu.removeAll();
	appMenu.add({
		text : I18N.frame_app_switch_title,
		cls : "title"
	});
	appMenu.addSeparator();
	for (var i = 0; i < h.length; i++) {
		if (h[i].current) {
			appMenu.add({
				id : h[i].id,
				text : h[i].name,
				cls : "curapp",
				handler : appMenuAction
			})
		} else {
			appMenu.add({
				id : h[i].id,
				text : h[i].name,
				handler : appMenuAction
			})
		}
	}
	var k = g.getHeight();
	appMenu.showAt([ g.getX(), g.getY() + k - 2 ])
}
function showBtn(b) {
	addStyle = Ext.get(b).dom.style;
	addStyle.display = "block"
}
function hiddenBtn(b) {
	addStyle = Ext.get(b).dom.style;
	addStyle.display = "none"
}
var helpMenu = new Ext.menu.Menu({
	id : "toolMenu",
	minWidth : 90
});
var appMenu = new Ext.menu.Menu({
	id : "appMenu",
	minWidth : 90,
	cls : "appSwitch"
});
var toolMenu = new Ext.menu.Menu({
	id : "toolMenu",
	minWidth : 90
});
var langMenu = new Ext.menu.Menu({
	id : "langMenu",
	minWidth : 90
});
function showDownLetMenus(i) {
	var g = new Ext.Element(i);
	var h = g.getXY();
	if (i.id == "tool") {
		toolMenu.showAt([ h[0], h[1] + g.getHeight() ])
	} else {
		if (i.id == "help") {
			var k = getApplication(CurrentApp.appID);
			var l = !(k.helpurl == null || k.helpurl == "");
			var j = helpMenu.items.get("help");
			j.setVisible(l);
			helpMenu.showAt([ h[0], h[1] + g.getHeight() ])
		}
	}
}
var nodeMenu = new Ext.menu.Menu({
	id : "r1node"
});
Ext.onReady(function() {
	if (!toolMenu) {
		toolMenu = new Ext.menu.Menu({
			id : "toolMenu",
			minWidth : 90
		})
	}
	if (Global.idType != "3") {
	}
	toolMenu.add({
		id : "change",
		text : I18N.frame_toolset_info_change,
		cls : "rone-menu-update",
		iconCls : "info-change",
		hidden : !Global.toolMenu.change,
		handler : toolMenuAction
	}, {
		id : "custom",
		text : I18N.portal_frame_toolbar_custom,
		cls : "rone-menu-update",
		iconCls : "tool-custom",
		hidden : !Global.toolMenu.custom,
		handler : toolMenuAction
	}, {
		id : "favorites",
		text : I18N.frame_toolset_addto_favorites,
		cls : "rone-menu-update",
		iconCls : "as-firstpage",
		hidden : !Global.toolMenu.favorites,
		handler : toolMenuAction
	}, {
		id : "setDefault",
		text : I18N.portal_frame_toolbar_homepage,
		cls : "rone-menu-update",
		iconCls : "addto-favorites",
		hidden : !Global.toolMenu.setDefault,
		handler : toolMenuAction
	}, {
		id : "resetDefault",
		text : I18N.portal_frame_toolbar_default,
		cls : "rone-menu-update",
		iconCls : "addto-favorites",
		hidden : !Global.toolMenu.resetDefault,
		handler : toolMenuAction
	}, {
		id : "whereami",
		text : I18N.frame_tool_whereami,
		iconCls : "display-icon",
		hidden : !Global.toolMenu.whereami,
		handler : toolMenuAction
	}, {
		id : "pulish",
		text : I18N.portal_frame_toolbar_pulish,
		cls : "rone-menu-update",
		iconCls : "pulish",
		hidden : !Global.toolMenu.pulish,
		handler : toolMenuAction
	}, {
		id : "node",
		text : I18N.frame_toolset_visit_other_node,
		cls : "rone-menu-delete",
		iconCls : "other-node",
		hidden : !Global.toolMenu.node,
		handler : toolMenuAction,
		menu : null
	});
	helpMenu.add({
		id : "help",
		text : I18N.frame_toolset_help_content,
		iconCls : "help-ico",
		handler : toolMenuAction
	}, {
		id : "psgishelp",
		text : I18N.psgis_help,
		iconCls : "poralhelp-ico",
		handler : toolMenuAction
	}, {
		id : "portalhelp",
		text : I18N.frame_potal_help,
		iconCls : "poralhelp-ico",
		hidden : !Global.toolMenu.portalHelp,
		handler : toolMenuAction
	}, {
		id : "aboutus",
		text : I18N.copyright_info,
		cls : "rone-menu-copy",
		iconCls : "license-info",
		handler : toolMenuAction
	});
	langMenu = new Ext.menu.Menu({
		id : "langMenu",
		minWidth : 70
	})
});
function setIframeHeight() {
	var f = document.body.clientHeight;
	var e = document.body.clientWidth;
	var d = Ext.get("main").getHeight();
	Ext.get("footFrame").setHeight(f - d);
	Ext.get("footFrame").setWidth(e)
}
function rezise() {
	var b = document.body.clientWidth;
	if (b < 800) {
		window.scrollbar = true;
		Ext.get("footFrame").setWidth(800)
	} else {
		Ext.get("footFrame").setWidth(b)
	}
	setIframeHeight();
	if (window.top.footFrame.main.tabPanel) {
		setTimeout("window.top.footFrame.main.tabPanel.syncSize();", 100)
	}
	r1message.display.resize()
}
function hidenHeader() {
	var f = document.getElementById("mg-outer");
	var e = document.getElementById("header");
	var d = document.getElementById("headerhidenicon");
	if (e.style.display == "block") {
		e.style.display = "none";
		d.src = Global.skinpath + "/rone/images/frame/show.gif"
	} else {
		e.style.display = "block";
		d.src = Global.skinpath + "/rone/images/frame/hide.gif"
	}
	window.top.setIframeHeight();
	if (typeof (window.top.footFrame.main.syncSize) == "function") {
		window.top.footFrame.main.syncSize()
	}
}
function hidenHeaders() {
	var c = document.getElementById("header");
	var d = document.getElementById("headerhidenicon");
	c.style.display = "none";
	d.src = Global.skinpath + "/rone/images/frame/show.gif";
	setIframeHeight()
}
function justHideHeader() {
	var f = document.getElementById("mg-outer");
	var e = document.getElementById("header");
	var d = document.getElementById("headerhidenicon");
	e.style.display = "none";
	d.src = Global.skinpath + "/rone/images/frame/show.gif";
	window.top.setIframeHeight();
	if (typeof (window.top.footFrame.main.syncSize) == "function") {
		window.top.footFrame.main.syncSize()
	}
}
function takeEffect(b) {
	Ext.MessageBox.confirm(I18N.layoutSkin_confirm_title,
			I18N.layoutSkin_confirm, function(a) {
				if (a == "yes") {
					roneSwitch("app", b)
				}
			})
}
var menulast;
function openMgTab(h, g, j) {
	if (h && h != "" && h != "#" && h != "undefined") {
		if (typeof j == "undefined") {
			j = "tab_mg_" + new Date().getDate()
		}
		CurrentMenu.init();
		CurrentMenu.mgid = j.substring(3);
		CurrentMenu.mgName = g;
		var f = new window.top.footFrame.main.RoneTab(j, g, null, "ro",
				OpenType.Nav, TargetTab.Auto);
		var i = window.top.footFrame.left.leftFrame;
		if (!i) {
			i = new window.top.footFrame.main.LeftFrame()
		}
		i.nav_title = g;
		h = getTokenUrl(h);
		i.nav_tabSrc = h;
		f.leftFrame = i;
		window.top.footFrame.main.justOpen(f)
	}
}
function changeZindex(g) {
	var e = getApplication(CurrentApp.appID);
	var f = getMenugroupOfApp(e);
	if (g != "") {
		Ext.get("menugroup_" + g).applyStyles("z-index:" + f.length);
		for (var h = 0; h < f.length; h++) {
			if (f[h].id != g) {
				Ext.get("menugroup_" + f[h].id).applyStyles(
						"z-index:" + (f.length - 1 - h))
			}
		}
	}
}
function hiddenMenus(b) {
	if (b) {
		document.getElementById("menu-scroller-outer").style.display = "none"
	} else {
		document.getElementById("menu-scroller-outer").style.display = "block"
	}
	setIframeHeight();
	if (typeof (window.top.footFrame.main.syncSize) == "function") {
		window.top.footFrame.main.syncSize()
	}
}
function showMsgToolbar() {
	Ext.get("msgtoolbar").applyStyles("visibility:visible")
}
function hideMsgToolbar() {
	Ext.get("msgtoolbar").applyStyles("visibility:hidden")
}
var r1message = {
	width : 300,
	minWidth : 80,
	messages : new Array(),
	currentMsgIndex : 0,
	addMsg : function(c) {
		var d = this.messages.length;
		this.messages[d] = c
	},
	getMsg : function(b) {
		return this.messages[b]
	},
	getCurrentMsg : function() {
		if (this.currentMsgIndex >= this.messages.length) {
			return this.messages[0]
		} else {
			return this.messages[this.currentMsgIndex]
		}
	},
	cleanMsg : function() {
		this.messages.length = 0
	},
	changeMsgContent : function(b) {
		document.getElementById("whereAmIDetail").title = b.toolTip;
		document.getElementById("whereAmITitleTxt").innerHTML = b.title;
		document.getElementById("whereAmIDetail").innerHTML = b.message
	},
	preMsg : function() {
		this.currentMsgIndex--;
		var b = this.getCurrentMsg();
		this.changeMsgContent(b);
		b.setIsNew(false)
	},
	nextMsg : function() {
		this.currentMsgIndex++;
		var b = this.getCurrentMsg();
		this.changeMsgContent(b);
		b.setIsNew(false)
	}
};
var r1messageContent = function(f, j, g, h) {
	this.title = f;
	this.miniTitle = j;
	this.message = g;
	this.toolTip = h;
	var i = true;
	this.setIsNew = function(a) {
		i = a
	};
	this.getIsNew = function(a) {
		return i
	}
};
r1message.init = function() {
	var l = I18N.whereami_title;
	var k = I18N.whereami_minititle;
	var h = '<span style="font-weight:bold">' + I18N.whereami_body
			+ "</span><br/>&nbsp;&nbsp;" + CurrentApp.sysName + " > "
			+ CurrentApp.appGrpName + " > " + CurrentApp.appName;
	var i = I18N.whereami_tooltip;
	var g = new r1messageContent(l, k, h, i);
	r1message.cleanMsg();
	r1message.addMsg(g);
	var j = r1message.getMsg(0);
	r1message.changeMsgContent(j);
	Ext.get("whereAmI").setWidth(r1message.width)
};
var showInterval;
var hideInterval;
var hideTimeout;
var showTimeout;
var currentPostion;
r1message.display = {
	width : null,
	left : null,
	maxTop : null,
	minTop : null,
	maxAppearTime : 3000,
	todisplay : function() {
		var b = Ext.get("whereAmI").getWidth();
		if (b < r1message.width) {
			this.show()
		} else {
			this.miniaze()
		}
	},
	show : function() {
		var j = Ext.get("whereAmI");
		var i = Ext.get("whereAmIDetail");
		j.applyStyles("display:block");
		i.applyStyles("display:block");
		var g = r1message.getCurrentMsg();
		r1message.changeMsgContent(g);
		g.setIsNew(false);
		document.getElementById("whereamimg").src = Global.skinpath
				+ "/rone/images/frame/minimize.gif";
		j.setWidth(r1message.width);
		var f = j.getHeight() + 5;
		var h = r1message.width + 10;
		this.left = document.body.clientWidth - h;
		this.maxTop = document.body.clientHeight;
		this.minTop = document.body.clientHeight - f;
		currentPostion = this.maxTop;
		j.position("absolute", 100, r1message.display.left, currentPostion);
		if (showInterval) {
			clearInterval(showInterval)
		}
		if (hideInterval) {
			clearInterval(hideInterval)
		}
		showInterval = setInterval("animateShow()", 10)
	},
	miniaze : function() {
		var k = Ext.get("whereAmI");
		var g = Ext.get("whereAmITitle");
		var j = Ext.get("whereAmIDetail");
		j.applyStyles("display:none");
		var h = r1message.getCurrentMsg();
		document.getElementById("whereAmITitleTxt").innerHTML = h.miniTitle;
		document.getElementById("whereamimg").src = Global.skinpath
				+ "/rone/images/frame/revert.gif";
		k.setWidth(r1message.minWidth);
		var l = g.getHeight() + 5;
		var i = r1message.minWidth + 10;
		k.position("absolute", 100, document.body.clientWidth - i,
				document.body.clientHeight - l)
	},
	close : function() {
		stopHideCount();
		if (hideInterval) {
			clearInterval(hideInterval)
		}
		if (showInterval) {
			clearInterval(showInterval)
		}
		hideInterval = setInterval("animateHide()", 10)
	},
	resize : function() {
		var e = Ext.get("whereAmI");
		var f = e.getHeight() + 5;
		var d = e.getWidth() + 10;
		e.position("absolute", 100, document.body.clientWidth - d,
				document.body.clientHeight - f)
	}
};
function stopHideCount() {
	clearTimeout(hideTimeout)
}
function startHideCount() {
	clearTimeout(hideTimeout);
	hideTimeout = setTimeout("r1message.display.close()",
			r1message.display.maxAppearTime)
}
function animateShow() {
	if (currentPostion <= r1message.display.minTop) {
		if (showInterval) {
			clearInterval(showInterval)
		}
		startHideCount();
		document.getElementById("whereAmI").onmouseover = stopHideCount;
		document.getElementById("whereAmI").onmouseout = startHideCount
	} else {
		var b = Ext.get("whereAmI");
		currentPostion = currentPostion - 1;
		b.position("absolute", 100, r1message.display.left, currentPostion)
	}
}
function animateHide() {
	if (currentPostion >= r1message.display.maxTop) {
		if (hideInterval) {
			clearInterval(hideInterval)
		}
		Ext.get("whereAmI").applyStyles("display:none");
		r1message.currentMsgIndex++;
		if (r1message.currentMsgIndex >= r1message.messages.length) {
			r1message.currentMsgIndex = 0
		} else {
			var d = r1message.getCurrentMsg();
			if (d.getIsNew()) {
				r1message.display.show()
			} else {
				r1message.currentMsgIndex = 0
			}
		}
	} else {
		var c = Ext.get("whereAmI");
		currentPostion = currentPostion + 1;
		c.position("absolute", 100, r1message.display.left, currentPostion)
	}
}
function drag(b) {
	b.onmousedown = function(h) {
		var g = document;
		if (!h) {
			h = window.event
		}
		var a = h.layerX ? h.layerX : h.offsetX, d = h.layerY ? h.layerY
				: h.offsetY;
		if (b.setCapture) {
			b.setCapture()
		} else {
			if (window.captureEvents) {
				window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
			}
		}
		g.onmousemove = function(e) {
			if (!e) {
				e = window.event
			}
			if (!e.pageX) {
				e.pageX = e.clientX
			}
			if (!e.pageY) {
				e.pageY = e.clientY
			}
			var f = e.pageX - a, j = e.pageY - d;
			var c = document.getElementById("whereAmI");
			c.style.left = f;
			c.style.top = j
		};
		g.onmouseup = function() {
			if (b.releaseCapture) {
				b.releaseCapture()
			} else {
				if (window.captureEvents) {
					window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
				}
			}
			g.onmousemove = null;
			g.onmouseup = null
		}
	}
}
var scroller = {
	srollVar : null,
	div_scroll : null,
	distance : 8,
	interval : 5,
	lastEL : null,
	leftBut : null,
	rightBut : null,
	right_range : 30,
	type : null
};
scroller.Marquee = function(b) {
	if (!this.isToStop(b)) {
		if (b) {
			this.div_scroll.scrollLeft = this.div_scroll.scrollLeft
					+ this.distance
		} else {
			this.div_scroll.scrollLeft = this.div_scroll.scrollLeft
					- this.distance
		}
	}
};
scroller.el = function(b) {
	return b ? document.getElementById(b) : null
};
scroller.show = function(e, h) {
	var f = this.el(e);
	if (!f) {
		return
	}
	var g = h ? "block" : "none";
	f.style.display = g
};
scroller.doMarquee = function(d, c) {
	this.init(d);
	this.marqueeStop();
	if (c == "left") {
		this.srollVar = setInterval("scroller.Marquee(true)", this.interval)
	} else {
		this.srollVar = setInterval("scroller.Marquee(false)", this.interval)
	}
};
scroller.init = function(b) {
	this.type = b;
	if (b == 1) {
		this.right_range = 150;
		this.div_scroll = document.getElementById("mg-scroller");
		this.lastEL = mglast;
		this.leftBut = Ext.get("mg-left");
		this.rightBut = Ext.get("mg-right")
	} else {
		if (b == 2) {
			this.right_range = 120;
			this.div_scroll = document.getElementById("menu-scroller");
			this.lastEL = menulast;
			this.leftBut = Ext.get("menu-left");
			this.rightBut = Ext.get("menu-right")
		}
	}
};
scroller.isToStop = function(b) {
	if (b) {
		if ((this.rightBut.getLeft() - this.lastEL.getLeft() - this.right_range) > 0) {
			this.leftBut.dom.style.visibility = "visible";
			this.rightBut.dom.style.visibility = "hidden";
			this.marqueeStop();
			return true
		} else {
			this.leftBut.dom.style.visibility = "visible";
			this.rightBut.dom.style.visibility = "visible";
			return false
		}
	} else {
		if (this.div_scroll.scrollLeft == 0) {
			this.rightBut.dom.style.visibility = "visible";
			this.leftBut.dom.style.visibility = "hidden";
			this.marqueeStop();
			return true
		} else {
			this.leftBut.dom.style.visibility = "visible";
			this.rightBut.dom.style.visibility = "visible";
			return false
		}
	}
};
scroller.marqueeStop = function() {
	clearInterval(this.srollVar);
	clearInterval(this.srollVar2)
};
scroller.reset = function() {
	document.getElementById("mg-scroller").scrollLeft = 0;
	document.getElementById("menu-scroller").scrollLeft = 0
};
scroller.run = function() {
	document.getElementById("mg-left").onmousedown = function() {
		scroller.doMarquee(1, "right")
	};
	document.getElementById("mg-left").onmouseup = function() {
		scroller.marqueeStop()
	};
	document.getElementById("mg-right").onmousedown = function() {
		scroller.doMarquee(1, "left")
	};
	document.getElementById("mg-right").onmouseup = function() {
		scroller.marqueeStop()
	};
	document.getElementById("menu-left").onmousedown = function() {
		scroller.doMarquee(2, "right")
	};
	document.getElementById("menu-left").onmouseup = function() {
		scroller.marqueeStop()
	};
	document.getElementById("menu-right").onmousedown = function() {
		scroller.doMarquee(2, "left")
	};
	document.getElementById("menu-right").onmouseup = function() {
		scroller.marqueeStop()
	};
	var c = document.getElementById("mg-down");
	if (c) {
		c.onclick = function(a) {
			scroller.showDownMenuList(a, "mg")
		}
	}
	var d = document.getElementById("menu-down");
	if (d) {
		d.onclick = function(a) {
			scroller.showDownMenuList(a, "menu")
		}
	}
};
scroller.moveToRightPlace = function(c, d) {
	this.init(c);
	this.targetEl = d;
	this.marqueeStop();
	this.srollVar2 = setInterval("scroller.doMoveToRightPlace()", this.interval)
};
scroller.doMoveToRightPlace = function() {
	var h = Ext.get(this.targetEl);
	var i = h.getLeft();
	var f = h.getRight();
	var g = this.distance + 10;
	var j = this.rightBut.getLeft() - 15;
	if (i >= g && f <= j) {
		this.marqueeStop()
	} else {
		if (i >= g) {
			this.div_scroll.scrollLeft = this.div_scroll.scrollLeft
					+ (this.distance + 5);
			if (Config.isSlide) {
				this.isToStop(true)
			}
		} else {
			if (f <= j) {
				this.div_scroll.scrollLeft = this.div_scroll.scrollLeft
						- (this.distance + 5);
				if (Config.isSlide) {
					this.isToStop(false)
				}
			}
		}
	}
};
scroller.showDownMenuList = function(c, d) {
	showDownMenu("auto", d);
	if (d == "menu") {
		activeDownListMenu()
	} else {
		activeDownListMg()
	}
};
function showDownMenu(h, i) {
	var f = Ext.get("down-scoller");
	if (h == "auto") {
		h = !(f.dom.style.display == "block")
	}
	if (h) {
		f.dom.style.display = "block";
		var g;
		if (i == "menu") {
			g = Ext.get("menu-down");
			f.replaceClass("mg-down", "menu-down")
		} else {
			g = Ext.get("mg-down");
			f.replaceClass("menu-down", "mg-down")
		}
		var j = g.getXY();
		f.setWidth(150);
		f.dom.style.display = "block";
		j[0] = j[0] - f.getWidth();
		j[1] = j[1] + g.getHeight();
		f.setXY(j)
	} else {
		f.dom.style.display = "none"
	}
	return !h
}
function doShowDownMenuList(c, d) {
	showDownMenu(false);
	showMainTab(c, d)
}
function _openMg(d) {
	showDownMenu(false);
	var c = "menugroup_" + d;
	if (Config.dropDownWithSlide) {
		scroller.moveToRightPlace(1, c)
	}
	document.getElementById(c).onclick()
}
function _openMenu(f, d) {
	showDownMenu(false);
	var e = "menu_" + d;
	if (Config.dropDownWithSlide) {
		scroller.moveToRightPlace(2, e)
	}
	document.getElementById(e).onclick()
}
function activeDefult() {
	Ext.get("menugrouplist").query(".menugroup")[0].onclick()
}
Ext.MessageBox.buttonText = {
	ok : I18N.common_ok,
	cancel : I18N.cancel,
	yes : I18N.common_yes,
	no : I18N.common_no
};
function timeout(d) {
	d = (typeof (d) == "undefined" || d == null) ? "" : d;
	d = d != "" ? ",提示码：" + d : d;
	var c = I18N.timeout + d;
	if (Ext.Window) {
		Ext.MessageBox.alert(I18N.common_remind, c, function(a) {
			go(Global.path + "/")
		});
		return
	}
	alert(c);
	go(Global.path + "/")
}
Array.prototype.swap = function(e, f) {
	var d = this[e];
	this[e] = this[f];
	this[f] = d
};
Array.prototype.bubbleSort = function() {
	for (var c = this.length - 1; c > 0; --c) {
		for (var d = 0; d < c; ++d) {
			if (this[d].order < this[d + 1].order) {
				this.swap(d, d + 1)
			}
		}
	}
};
function loadToolJS() {
	Loader.load("ext-tool-js", Global.path + "/includes/ext-min/ext-tool."
			+ Global.jsCtrl);
	Loader.load("tool-js", Global.path + "/includes/rone-ext/tool."
			+ Global.jsCtrl)
}
CurrentBusinessApp = {
	appuuid : null,
	appname : null
};
var MenuAudit = {
	IMPORTANT_FUN : [ "构件管理", "组织管理", "用户管理" ],
	TIME_SPAN : 10000,
	RECORD_NUM : 10,
	cachedRecords : new Array(),
	mgName : "",
	menuName : "",
	resName : "",
	BusinessApp : {
		appuuid : null,
		appname : null
	},
	isSubmit : false,
	doAudit : function(f) {
		var e = true;
		if (f == MG_TYPE && this.mgName == CurrentMenu.mgName) {
			e = false
		} else {
			if (f == MODULE_TYPE && this.menuName == CurrentMenu.menuName) {
				e = false
			} else {
				if (f == RES_TYPE && this.resName == CurrentMenu.resName) {
					e = false
				} else {
					if (f == APP_TYPE
							&& this.BusinessApp == CurrentBusinessApp.appuuid) {
						e = false
					}
				}
			}
		}
		if (e) {
			if (f == MG_TYPE) {
				var d = new SingleRecord(f, CurrentMenu.mgId, "",
						CurrentMenu.mgName);
				if (this.isImportant(CurrentMenu.mgName)) {
					this.isSubmit = true
				}
			} else {
				if (f == MODULE_TYPE) {
					var d = new SingleRecord(f, CurrentMenu.menuId,
							CurrentMenu.moduleId, CurrentMenu.menuName);
					if (this.isImportant(CurrentMenu.menuName)) {
						this.isSubmit = true
					}
				} else {
					if (f == RES_TYPE) {
						var d = new SingleRecord(f, CurrentMenu.resId,
								CurrentMenu.moduleId, CurrentMenu.resName);
						if (this.isImportant(CurrentMenu.resName)) {
							this.isSubmit = true
						}
					} else {
						if (f == APP_TYPE) {
							var d = new AppVisitedRecord(f,
									CurrentBusinessApp.appuuid,
									CurrentBusinessApp.appname);
							this.isSubmit = true
						}
					}
				}
			}
			this.cachedRecords.push(d);
			this.sendAudit();
			this.mgName = CurrentMenu.mgName;
			this.menuName = CurrentMenu.menuName;
			this.resName = CurrentMenu.resName
		}
	},
	auditDropDownMg : function() {
		if (this.mgName != CurrentMenu.mgName) {
			var b = new SingleRecord(MG_TYPE, CurrentMenu.mgId, "",
					CurrentMenu.mgName);
			if (this.isImportant(CurrentMenu.mgName)) {
				this.isSubmit = true
			}
			this.cachedRecords.push(b);
			this.sendAudit();
			this.mgName = CurrentMenu.mgName;
			this.menuName = "";
			this.resName = ""
		}
	},
	sendAudit : function() {
		if (this.cachedRecords.length >= this.RECORD_NUM) {
			this.isSubmit = true
		}
		if (this.isSubmit && this.cachedRecords.length > 0) {
			Ext.Ajax.request({
				url : Global.path + "/MenuVistitedServlet",
				params : {
					auditJson : this.arrayToJson()
				},
				success : function(b) {
				},
				failure : function(b) {
				}
			});
			this.isSubmit = false;
			this.cachedRecords = this.cachedRecords.splice(0,
					this.cachedRecords.lenght - 1)
		}
	},
	arrayToJson : function() {
		var e = new Array();
		e.push("[");
		for (var f = 0; f < this.cachedRecords.length; f++) {
			var d = this.cachedRecords[f].MENUOBJID
					|| this.cachedRecords[f].APPUUID;
			if (d && d != "null" && d != "") {
				if (e.length > 1) {
					e.push(",")
				}
				e.push(this.cachedRecords[f].toJson())
			}
		}
		e.push("]");
		return e.join("")
	},
	isImportant : function(d) {
		for (var c = 0; c < this.IMPORTANT_FUN.length; c++) {
			if (d == this.IMPORTANT_FUN[c]) {
				return true
			}
		}
	},
	doSendOntime : function() {
		this.isSubmit = true;
		this.sendAudit()
	}
};
setInterval("MenuAudit.doSendOntime()", MenuAudit.TIME_SPAN);
var SingleRecord = function(h, f, e, g) {
	this.menuObjType = h;
	this.MENUOBJID = f == null ? "" : f;
	this.MODUUID = e == null ? "" : e;
	this.MENUOBJNAME = g == null ? "" : g;
	this.VISITED_TIME = new Date().getTime();
	this.toJson = function() {
		return "{'OBJECTTYPE':" + this.menuObjType + ",'MENUOBJID':'"
				+ this.MENUOBJID + "','MODUUID':'" + this.MODUUID
				+ "','MENUOBJNAME':'" + this.MENUOBJNAME + "','VISITED_TIME':'"
				+ this.VISITED_TIME + "'}"
	}
};
var AppVisitedRecord = function(f, d, e) {
	this.menuObjType = f;
	this.APPUUID = d == null ? "" : d;
	this.APPNAME = e == null ? "" : e;
	this.VISITED_TIME = new Date().getTime();
	this.toJson = function() {
		return "{'OBJECTTYPE':" + this.menuObjType + ",'APPUUID':'"
				+ this.APPUUID + "','APPNAME':'" + this.APPNAME
				+ "','VISITED_TIME':'" + this.VISITED_TIME + "'}"
	}
};
function setSataus() {
	var b = I18N.frame_status_hi;
	if (Global.user.orgName && Global.user.orgName != ""
			&& Global.user.orgName != "null") {
		b += I18N.frame_status_from + Global.user.orgName
				+ I18N.frame_status_from_end
	}
	b += Global.user.name;
	if (CurrentMenu.mgName) {
		b += I18N.frame_status_where + CurrentMenu.mgName;
		b += CurrentMenu.menuName ? " > " + CurrentMenu.menuName : "";
		b += CurrentMenu.resName ? " > " + CurrentMenu.resName : ""
	}
	window.status = b
}
function doLogout(c) {
	MenuAudit.doSendOntime();
	var d = window.confirm(I18N.tool_logout_tip);
	if (d) {
		Global.logout.destroyWhenLeft = false;
		window.location.href = c
	}
}
function getAccountRule() {
	if (Global.idType == "3") {
	}
	Ext.Ajax.request({
		url : Global.path + "/PersonHelperServlet",
		params : {
			flag : "getAccountRule"
		},
		success : function(response) {
			Global.accountRule = eval("(" + response.responseText + ")");
			if (Global.accountRule.policystate != 2
					&& Global.accountRule.haveToChangePwd == "true") {
				Loader.load("check-css", Global.consoleSkinPath
						+ "/rone/style/check." + Global.cssCtrl);
				Loader.ajaxLoad([
						Global.path + "/includes/ext-min/ext-tool."
								+ Global.jsCtrl,
						Global.path + "/includes/rone-ext/tool."
								+ Global.jsCtrl ], function() {
					PasswordCheck.forceUpdate()
				})
			}
		}
	})
}
RoneLoad = {
	nodes : null,
	loadNeed : function() {
		Loader.showLoadMark = false;
		Loader.ajaxLoad([
				Global.path + "/includes/ext-min/ext-tool." + Global.jsCtrl,
				Global.path + "/includes/rone-ext/tool." + Global.jsCtrl ],
				function() {
					RoneLoad.trustedNode()
				});
		Loader.showLoadMark = true
	},
	trustedNode : function() {
		Ext.Ajax.request({
			url : Global.path + "/RoDomainServlet",
			params : {
				flag : "toList",
				rType : "json"
			},
			success : function(response) {
				var nodes = eval("(" + response.responseText + ")");
				if (nodes.totalCount == 0) {
					return
				}
				RoneLoad.nodes = nodes.rows;
				nodeMenu.add({
					id : "current",
					text : "返回本部系统",
					cls : "rone-menu-update",
					iconCls : "pulish",
					handler : RoneLoad.nodeAction
				}, "-");
				for (var i = 0; i < nodes.totalCount; i++) {
					nodeMenu.add({
						id : nodes.rows[i].nodeCode,
						text : nodes.rows[i].nodeName,
						cls : "rone-menu-update",
						iconCls : "pulish",
						handler : RoneLoad.nodeAction
					})
				}
				toolMenu.items.get("node").menu = nodeMenu
			}
		})
	},
	nodeAction : function(g) {
		var e = Global.path;
		var f = "";
		for (var h = 0; h < RoneLoad.nodes.length; h++) {
			if (RoneLoad.nodes[h].nodeCode == g.id) {
				e = RoneLoad.nodes[h].context;
				f = RoneLoad.nodes[h].server;
				break
			}
		}
		roneSwitch("node", "", f, e)
	}
};
CloseHook = {
	refresh : false,
	handleKeyDown : function(c) {
		var d = c.getKey();
		CloseHook.refresh = ((c.ctrlKey && d == 82) || d == 116)
	},
	doBeforeunload : function(f) {
		if (Global.logout.destroyWhenLeft == true && !CloseHook.refresh) {
			var d = '确定要离开 "' + document.title + '" 吗？';
			var e = f || window.event;
			if (e) {
				e.returnValue = d
			}
			return d
		}
	},
	doUnload : function(c) {
		if (Global.logout.destroyWhenLeft == true && !CloseHook.refresh) {
			var d = Ext.lib.Ajax.getConnectionObject().conn;
			d.open("GET", Global.path + "/logout", false);
			d.send(null)
		}
	},
	run : function() {
		Ext.EventManager.on(document, "keydown", CloseHook.handleKeyDown);
		window.onbeforeunload = CloseHook.doBeforeunload;
		window.onunload = CloseHook.doUnload
	}
};
CloseHook.run();
function go(b) {
	Global.logout.destroyWhenLeft = false;
	window.location = b
}
function isFilteredApp() {
};