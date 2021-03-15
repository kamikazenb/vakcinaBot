# BOT PRE REGISTRACIU NA OCKOVANIE

Bot je určený na automatickú obnovu webovej stránky s dotazníkom do doby,
kým vo zvolenom kraji nie je voľné miesto, pričom bot vyplní zadané údaje,
takže bude stačiť zvoliť miesto očkovania.

Ak bot zistí volné miesto, tak prestane obnovovať stránku a použítela upozorní
zvukom (rovnakým ako pri zapnutí bota).

### !! Počas činnosti bota nehýbať s oknom, nescollovať a ani na nič neklikať !!


kamikazenb@gmail.com

# špecifikácie
- určené pre windows 10 (testované na Win10 64bit aj 32bit)
- JAVA minimálne verzia 10
   -> https://www.oracle.com/java/technologies/javase-jdk15-downloads.html (len 64bit)
   -> https://adoptopenjdk.net/index.html (32bit alternatíva)
- prehliadač mozilla firefox + potrebné stiahnuť ovládač bud z priečinka /download
  alebo priamo zo zdroja https://github.com/mozilla/geckodriver/releases

program sa nachádza v zložke /download/vakcinaBotX.x.exe
pred zapnutím potrebné zadať zdroj pre ovládač firefoxu (c:/.../geckodriver_firefox_x64.exe)

# verzie
## v1.2
### fuknčné 
* rodné číslo
* číslo domu
### nefunkčné ostáva
* skupina osôb do ktorej patríte
* mesto/obec registrovanej osoby
### reakcia na novinky zo strany štátu (zmeny vo formulári)
* bot padá v prípade ak stránka zahlási že niesú voľné kapacity bez možnosti vloženia údajov
  (časom isto fixnem)
* Avizované viackrokové prihlasovanie isto funkčné nebude, uvidím či bude možný edit bota
## v1.1
### fix bugov
* pád stránky po načítaní
### nefuknčné
* občas výber poisťovne / číslo domu
## v1.0
### nefunkčné
* rodné číslo
* skupina osôb do ktorej patríte
* mesto/obec registrovanej osoby
### známe bugy
* občas zvykne po načítaní stránky bot zlyhať -> vypnúť, zapnúť bota




