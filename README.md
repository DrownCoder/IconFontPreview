# IconFontPreview
Iconfont预览插件
##前言
使用过IconFont的应该都有这个痛楚，每次使用IconFont都需要找到对应的Unicode的值，然后到对应的目录路径找到定义IconFont和
Unicode的对应关系的文件并打开，而且当IconFont的每次更新，这个文件的路径可能会改变，就很烦恼，所以就想做一个类似于和色值的
预览的一样的插件，方便开发使用

## 简介
本插件用于预览项目下的IconFont文件，预览的是在string.xml中定义的IconFont

## 使用说明
第一次设置工程下ttf字体文件路径和定义iconfont的string.xml路径， 点击确认后便会生成Html文件，预览定义的IconFont资源
(缓存目录在项目根文件夹下的PluginCache)

### 1.点击操作面版的草帽icon(ONE PIECE)
![第一步](https://github.com/DrownCoder/IconFontPreview/blob/master/step2.jpg?raw=true)

### 2.第一次设置工程下ttf字体文件路径和定义iconfont的string.xml路径
![第二步](https://github.com/DrownCoder/IconFontPreview/blob/master/step1.jpg?raw=true)  
这一步只会在第一次才会使用，因为本地会有缓存，后面的会读取缓存的目录（根目录下的PlugCache）

### 3.点击确定后或者以后在点击草帽就会在浏览器中打开预览定义的IconFont图标了
![ONE PIECE](https://github.com/DrownCoder/IconFontPreview/blob/master/step3.jpg?raw=true)
