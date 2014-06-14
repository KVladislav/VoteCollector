package org.hillel.it.votecollector.messageBundle;

import org.hillel.it.votecollector.IOopperations.DatabaseIO;

import java.util.ListResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 24.12.13
 * Time: 10:28
 */
public class MessageBundle_ru extends ListResourceBundle {
    private static String type = "_ru";
    private static final String fileName = "VoteCollector/src/resources/messageBundle" + type + ".properties";
    private static final Object[][] contents = DatabaseIO.loadBundle(fileName);

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
