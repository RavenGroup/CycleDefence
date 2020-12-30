function links_Do(value) {
    const do_or_not = value;
    if (do_or_not === "1") {
        console.log(do_or_not)
        popmotion.tween({
            from: {
                opacity: 1,
                backgroundColor: '#fff'
            },
            to: {
                opacity: 0,
                backgroundColor: '#ebebeb'
            },
            duration: 500
        }).start(title.set)

        popmotion.tween({
            from: {
                backgroundColor: '#00004c',
                y: 0,
                opacity: 1
            },
            to: {
                backgroundColor: '#ebebeb',
                y: -150,
                opacity: 0
            },
            duration: 500
        }).start(header.set)

        popmotion.tween({
            from: {
                backgroundColor: '#fff',
                opacity: 1
            },
            to: {
                backgroundColor: '#ebebeb',
                opacity: 0
            },
            duration: 500
        }).start(cloud.set)
    }
}

const cloud = popmotion.styler(document.querySelector('.cloud-box'))
const title = popmotion.styler(document.querySelector('.title-page'))
const header = popmotion.styler(document.querySelector('header'))
const justtext = popmotion.styler(document.querySelector('.just-text'))

popmotion.tween({
    from: {
        color: '#ebebeb',
        backgroundColor: '#ebebeb'
    },
    to: {
        color: '#000',
        backgroundColor: '#fff'
    },
    duration: 500
}).start(justtext.set)


popmotion.tween({
    from: {
        opacity: 0,
        backgroundColor: '#ebebeb'
    },
    to: {
        opacity: 1,
        backgroundColor: '#fff'
    },
    duration: 500
}).start(title.set)

popmotion.tween({
    from: {
        backgroundColor: '#ebebeb',
        // {#scale: 0,#}
        y: -150,
        opacity: 0
    },
    to: {
        backgroundColor: '#00004c',
        // {#scale: 1,#}
        y: 0,
        opacity: 1
    },
    duration: 500
}).start(header.set)

popmotion.tween({
    from: {
        backgroundColor: '#ebebeb',
        // {#x: -3600,#}
        opacity: 0
    },
    to: {
        backgroundColor: '#fff',
        // {#x: 0,#}
        opacity: 1
    },
    duration: 500
}).start(cloud.set)
