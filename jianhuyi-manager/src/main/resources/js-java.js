document.write("<script type='text/javascript' src='b.js'></script>");
function gen() {
    let lightChart = echarts.init(document.getElementById("light"));
    let data1 = [];
    for (let da of res.lights) {
        data1.push({name: da.name, value: da.num});
    }
    var option1 = {

        tooltip: {trigger: 'item'},
        legend: {
            itemGap: 16,
            icon: 'rect',
            left: 'left',  //图例距离左的距离
            top: 'center',
            itemWidth: 150,
            itemHeight: 80,
            padding: [0, 300, 0, 0],
            orient: 'vertical',
            textStyle: {
                color: '##FFC8B4',
                fontSize: 30
            },
            //格式化图例文本
            formatter(name) {
                //找到data中name和文本name值相同的对象
                let val = res.lights.filter(item => {
                    return item.name === name
                })
                //            return name + '  ' + ((val[0].value / count).toFixed(4)) * 100 + '%'
                return name + '  ' + val[0].value;
            }
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '220px',
                center: ['60%', '55%'],
                data: data1,
                label: {
                    normal: {
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: 30
                        }
                    }
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    lightChart.setOption(option1);
    let distanceChart = echarts.init(document.getElementById("distance"));
    let data2 = [];
    for (let da of res.distances) {
        data2.push({name: da.name, value: da.num});
    }

    var option2 = {

        tooltip: {trigger: 'item'},
        legend: {
            itemGap: 16,
            icon: 'rect',
            left: 'left',  //图例距离左的距离
            top: 'center',
            itemWidth: 150,
            itemHeight: 80,
            padding: [0, 300, 0, 0],
            orient: 'vertical',
            textStyle: {
                color: '##FFC8B4',
                fontSize: 30
            },
            //格式化图例文本
            formatter(name) {
                //找到data中name和文本name值相同的对象
                let val = res.distances.filter(item => {
                    return item.name === name
                })
                //            return name + '  ' + ((val[0].value / count).toFixed(4)) * 100 + '%'
                return name + '  ' + val[0].value;
            }
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '220px',
                center: ['60%', '55%'],
                data: data2,
                label: {
                    normal: {
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: 30
                        }
                    }
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    distanceChart.setOption(option2);
    var sitChart = echarts.init(document.getElementById("sit"));
    let data3 = [];
    for (let da of res.sits) {
        data3.push({name: da.name, value: da.num});
    }
    var option3 = {

        tooltip: {trigger: 'item'},
        legend: {
            itemGap: 16,
            icon: 'rect',
            left: 'left',  //图例距离左的距离
            top: 'center',
            itemWidth: 150,
            itemHeight: 80,
            padding: [0, 300, 0, 0],
            orient: 'vertical',
            textStyle: {
                color: '##FFC8B4',
                fontSize: 30
            },
            //格式化图例文本
            formatter(name) {
                //找到data中name和文本name值相同的对象
                let val = res.sits.filter(item => {
                    return item.name === name
                })
                return name + '  ' + val[0].value;
            }
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '220px',
                center: ['60%', '55%'],
                data: data3,
                label: {
                    normal: {
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: 30
                        }
                    }
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    sitChart.setOption(option3);
    setTimeout(() => {
        let sn = sitChart.getDataURL().split("base64,")[1];
        let ln = lightChart.getDataURL().split("base64,")[1];
        let dn = distanceChart.getDataURL().split("base64,")[1];
        return sn + "JAVASCRIPT" + ln + "JAVASCRIPT" + dn;

    }, 1000);
}