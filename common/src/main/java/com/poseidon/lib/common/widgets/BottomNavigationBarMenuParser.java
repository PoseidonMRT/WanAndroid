package com.poseidon.lib.common.widgets;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.LayoutRes;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.core.internal.view.SupportMenu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BottomNavigationBarMenuParser {

    private static final String XML_MENU = "menu";
    private static final String XML_ITEM = "item";

    public static List<BottomNavigationBarMenuItem> inflate(Context context, @LayoutRes int menuRes) {
        XmlResourceParser parser = null;
        List<BottomNavigationBarMenuItem> results = new ArrayList<>();
        try {
            parser = context.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);

            parseMenu(context,parser, attrs,results);
        } catch (XmlPullParserException e) {
            throw new InflateException("Error inflating menu XML", e);
        } catch (IOException e) {
            throw new InflateException("Error inflating menu XML", e);
        } finally {
            if (parser != null) parser.close();
        }
        return results;
    }

    private static void parseMenu(Context context,XmlPullParser parser, AttributeSet attrs,List<BottomNavigationBarMenuItem> list)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }
                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;
        BottomNavigationBarMenuItem menuItem = null;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }

                    tagName = parser.getName();
                    if (tagName.equals(XML_ITEM)) {
                        menuItem = new BottomNavigationBarMenuItem(context,attrs);
                    } else {
                        lookingForEndOfUnknownTag = true;
                        unknownTagName = tagName;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } else if (tagName.equals(XML_ITEM)) {
                        // Add the item if it hasn't been added (if the item was
                        // a submenu, it would have been added already)
                        if (menuItem != null) {
                            list.add(menuItem);
                        }
                    } else if (tagName.equals(XML_MENU)) {
                        reachedEndOfMenu = true;
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                default:
                    throw new RuntimeException("Unexpected end of document");
            }

            eventType = parser.next();
        }
    }
}
