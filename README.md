# ÖppenHald

Kotlin implementation of the [HaldCLUT image processing format](https://rawpedia.rawtherapee.com/Film_Simulation).

## Example

|Source|RedBlueYellow|Lomo.. XProSlide200|Polaroid Polachrome|Fuji FP-100c 8|
|---|---|---|---|---|
|![](./reference_images/SAL_source.png)|![](./reference_images/SAL_RedBlueYellow.png)|![](./reference_images/SAL_Lomography_X_Pro_Slide_200.png)|![](./reference_images/SAL_Polaroid_Polachrome.png)|![](./reference_images/SAL_Fuji_FP-100c_8.png)|


## Usage

Download and unzip [OppenHald.jar](https://codeberg.org/attachments/741ad858-a085-44a2-a175-53b4d2862f51)

```
java -jar OppenHald.jar /path/to/haldclut.png /path/to/inputimage.png
```

Bash script to apply all HaldCLUT files to an image, created for use with the [RawTherapee Film Simulation Collection](https://rawpedia.rawtherapee.com/Film_Simulation#RawTherapee_Film_Simulation_Collection):

```
#!/bin/bash

echo "HaldCLUT Apply All"

shopt -s globstar
for f in ./*/*.png ./*/*/*.png ; do
    echo "processing $f"
    java -jar OppenHald.jar "$f" ./OP.png
done
```

![A preview image showing various HaldCLUT filters applied to a picture of some flowers](./contact_sheet_preview.png)

Creating a contact sheet using ImageMagick, with filenames:
```
magick montage -verbose -label '%f' -font Helvetica -pointsize 10 filtered/*.png -tile 12 -background '#000000' -fill '#dedede' -define png:size=200x200 -geometry 200x200+2+2 -auto-orient contact_sheet.png
```

and without:
```
magick montage -verbose -label '%f' filtered/*.png -tile 16 -background '#000000' -define png:size=200x200 -geometry 200x200+2+2 -auto-orient contact_sheet.png
```

Full contact sheet with HaldClut filenames for reference: [contact_sheet.png](contact_sheet.png)  **WARNING 43.5mb**

## Identity HaldCLUT

An identity HaldCLUT is a starting point for any custom filters where if applied the image remains untouched, a pixel value of #33AA00 on your photo will also be #33AA00 on the HaldCLUT. OppenHald can generate these identity CLUTS with a simple command, to generate a level 4 HaldCLUT: `java -jar OppenHald.jar 4`

Level 4 HaldCLUT:   
![Level 4 HaldCLUT Generated](./reference_images/generated_level4_identity.png)