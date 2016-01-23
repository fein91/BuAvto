var page = require('webpage').create(),
    system = require('system'),
    t, address;
var fs = require('fs');

if (system.args.length < 2) {
    console.log('Usage: loadHtml.js <some URL> <path> <timeout>');
    phantom.exit(1);
} else {
    t = Date.now();
    address = system.args[1];
    path = system.args[2];
    timeout = system.args[3];

    page.open(address, function (status) {
        if (status !== 'success') {
            console.log('FAIL to load the address');
            phantom.exit();
        } else {
            window.setTimeout(function () {
                var body = page.evaluate(function() {
                    return document.body.outerHTML;
                });

                fs.write(path, body, function() { console.log("done!") });

                t = Date.now() - t;
                console.log('Loading time ' + t + ' msec');
                phantom.exit();
            }, timeout);
        }
    });
}
