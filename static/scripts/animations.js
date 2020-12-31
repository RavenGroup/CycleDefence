function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}

function stupid_func() {
    sleep()
}

function links_Do() {
    popmotion.tween({
        from: {
            opacity: 1,
            backgroundColor: '#fff'
        },
        to: {
            opacity: 0,
            backgroundColor: '#ebebeb'
        },
        duration: 300
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
        duration: 300
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
        duration: 300
    }).start(cloud.set)
}

const cloud = popmotion.styler(document.querySelector('.cloud-box'))
const title = popmotion.styler(document.querySelector('.title-page'))
const header = popmotion.styler(document.querySelector('header'))
const body = popmotion.styler(document.querySelector('body'))

popmotion.tween({
    from: {
        opacity: 0
    },
    to: {
        opacity: 1
    },
    duration: 100
}).start(body.set)

popmotion.tween({
    from: {
        opacity: 0,
        backgroundColor: '#ebebeb'
    },
    to: {
        opacity: 1,
        backgroundColor: '#fff'
    },
    duration: 300
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
    duration: 300
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
    duration: 300
}).start(cloud.set)
