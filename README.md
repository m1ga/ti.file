# ti.file - Android file creator
Create files with a fixed size for quickly testing "out-of-space" scenarios.

## Methods
* **create({ filename: String, size: String})** - creates a new file. Size can be `{number}M` or `{number}G`.
* **deleteFiles()** - Deletes all files in the folder

## Events
* **writing** - returns `currentSize` and `maxSize` during the writing process

## ⚠ Attention ⚠
* It can be slow on some devices
* Make sure to remove the files when you are done!
* only use it for testing ;-)

## Example
```js
const win = Ti.UI.createWindow({layout:"vertical"});
const lbl = Ti.UI.createLabel({text: "-"});
const btn1 = Ti.UI.createButton({title: "create"});
const btn2 = Ti.UI.createButton({title: "delete"});
const fileTool = require("ti.file");

fileTool.addEventListener("writing", function(e) {
    lbl.text = Math.floor(e.currentSize / e.maxSize*100) + "%";
});

win.add([lbl, btn1, btn2]);
btn1.addEventListener("click", function() {
    fileTool.create({
        filename:"test",
        size:"10G"
    });
});

btn2.addEventListener("click", function() {
    fileTool.deleteFiles();
})
win.open();
```
