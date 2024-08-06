package ru.rodionkrainov.fastdevrkcustomuilib.uielements.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;

import ru.rodionkrainov.fastdevrkcustomuilib.GlobalFontsManager;
import ru.rodionkrainov.fastdevrkcustomuilib.FastDevRKCustomUILib;
import ru.rodionkrainov.fastdevrkcustomuilib.uielements.IRKUIElement;

public class RKLabel implements IRKUIElement {
    private String name;
    private int zIndex;
    private final int localZIndex;
    private boolean isPointerHover = false;
    private boolean isInFocus = false;

    private final FastDevRKCustomUILib LIB;

    private Color fillColor;
    private Color borderColor;
    private float alpha      = 1f;
    private float localAlpha = 1f;

    private boolean isImage;
    private RKImage image;

    private final int fontSize;
    private final Label label;

    public RKLabel(String _name, String _text, Color _color, int _fontSize, float _posX, float _posY, int _zIndex, int _localZIndex, FastDevRKCustomUILib _lib) {
        name   = _name;
        zIndex = _zIndex;
        localZIndex = _localZIndex;

        LIB = _lib;

        fillColor   = _color.cpy();
        borderColor = new Color(0, 0, 0, alpha);

        fontSize = _fontSize;

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = GlobalFontsManager.ARR_BP_FONTS_10_TO_50[ (Math.max(0, Math.min((_fontSize - 10), 40))) ];

        checkIsImg(_text);

        label = new Label(_text, labelStyle);
        label.setColor(fillColor);
        label.setPosition(_posX, _posY);
        label.setAlignment(Align.left);
        label.pack();
    }

    private void checkIsImg(String _text) {
        isImage = _text.contains("%#img_") && _text.contains("#%");
        if (isImage) {
            String imgTextureName = _text.substring(_text.indexOf("%#img_") + 6, _text.indexOf("#%"));
            image = LIB.addImage("label_" + name + "_img", imgTextureName, 0, 0, 0, 0, zIndex, localZIndex + 1);
        }
    }

    @Override
    public void update(float _delta, boolean[][] _pointersStates) {
        if (alpha > 0 && localAlpha > 0) {
            fillColor.a   = Math.min(alpha, localAlpha);
            borderColor.a = Math.min(alpha, localAlpha);

            if (isImage) {
                setSize(fontSize * 1.27f, fontSize * 1.27f);

                image.setSize(getWidth(), getHeight());
                image.setPosition(label.getX() - (getWidth() - label.getWidth()) / 2f, label.getY() - (getHeight() - label.getHeight()) / 2f);
            }

            label.setColor(fillColor);
            label.act(_delta);
        }
    }

    @Override
    public void draw(Batch _batch, ShapeRenderer _shapeRenderer, float _parentAlpha) {
        if (alpha > 0 && localAlpha > 0) {
            if (!isImage) label.draw(_batch, _parentAlpha);
        }
    }

    public boolean isHasImage() {
        return isImage;
    }

    @Override
    public void setVisible(boolean _isVisible) {
        label.setVisible(_isVisible);
    }

    @Override
    public void setIsPointerHover(boolean _isPointerHover) {
        isPointerHover = _isPointerHover;
    }

    @Override
    public boolean isVisible() {
        return label.isVisible();
    }

    @Override
    public boolean isPointerHover() {
        return isPointerHover;
    }

    @Override
    public void setName(String _name) {
        name = _name;
    }

    public void setText(String _text) {
        checkIsImg(_text);
        label.setText(_text);

        if (!isImage) label.pack();
    }

    @Override
    public void setPosition(float _x, float _y) {
        label.setPosition(_x, _y);
    }

    @Override
    public void setX(float _x) {
        label.setX(_x);
    }

    @Override
    public void setY(float _y) {
        label.setY(_y);
    }

    @Override
    public void setSize(float _w, float _h) {
        label.setSize(_w, _h);
    }

    @Override
    public void setWidth(float _w) {
        label.setWidth(_w);
    }

    @Override
    public void setHeight(float _h) {
        label.setHeight(_h);
    }

    @Override
    public void setFillColor(Color _color) {
        fillColor = _color.cpy();
        fillColor.a = Math.min(alpha, localAlpha);
    }

    @Override
    public void setBorderColor(Color _color) {
        borderColor = _color.cpy();
        borderColor.a = Math.min(alpha, localAlpha);
    }

    @Override
    public void setAlpha(float _alpha) {
        alpha = _alpha;
    }

    @Override
    public void setLocalAlpha(float _localAlpha) {
        localAlpha = _localAlpha;
    }

    @Override
    public void setZIndex(int _zIndex) {
        zIndex = _zIndex;
    }

    @Override
    public void setIsInFocus(boolean _isInFocus) {
        isInFocus = _isInFocus;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getText() {
        return label.getText().toString();
    }

    @Override
    public Vector2 getPosition() {
        return (new Vector2(label.getX(), label.getY()));
    }

    @Override
    public float getX() {
        return label.getX();
    }

    @Override
    public float getY() {
        return label.getY();
    }

    @Override
    public Vector2 getSize() {
        if (!isImage) label.pack();
        return (new Vector2(label.getWidth(), label.getHeight()));
    }

    @Override
    public float getWidth() {
        return label.getWidth();
    }

    @Override
    public float getHeight() {
        return label.getHeight();
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    @Override
    public float getAlpha() {
        return alpha;
    }

    @Override
    public float getLocalAlpha() {
        return localAlpha;
    }

    @Override
    public String getType() {
        return "label";
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public int getLocalZIndex() {
        return localZIndex;
    }

    @Override
    public boolean isInFocus() {
        return isInFocus;
    }

    @Override
    public void dispose() {
        label.remove();
    }
}