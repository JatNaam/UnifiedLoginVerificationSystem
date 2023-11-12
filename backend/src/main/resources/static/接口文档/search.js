let api = [];
api.push({
    alias: 'AdminController',
    order: '1',
    link: '管理员模块',
    desc: '管理员模块',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '获取注册申请',
});
api[0].list.push({
    order: '2',
    desc: '管理员通过用户的注册申请',
});
api[0].list.push({
    order: '3',
    desc: '获取所有已通过审核的用户',
});
api.push({
    alias: 'LoginController',
    order: '2',
    link: '登录验证模块',
    desc: '登录验证模块',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '密码登录',
});
api[1].list.push({
    order: '2',
    desc: '验证码登录',
});
api.push({
    alias: 'UserController',
    order: '3',
    link: '用户模块',
    desc: '用户模块',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '用户申请注册，待管理员审核',
});
api[2].list.push({
    order: '2',
    desc: '',
});
api[2].list.push({
    order: '3',
    desc: '生成二维码，APP直接扫描绑定，两种方式任选一种',
});
api[2].list.push({
    order: '4',
    desc: '开启无密码登录',
});
api[2].list.push({
    order: '5',
    desc: '获取用户是否开启无密码登录',
});
api.push({
    alias: 'dict',
    order: '4',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '常见返回码',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}