package com.android.cong.proxydemo.hook;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class LayoutTraverse {
    private int index = 0;

    public interface Processor {
        void process(View view);

        void traverseEnd(ViewGroup root);
    }

    private final Processor processor;

    private LayoutTraverse(Processor processor) {
        this.processor = processor;
    }

    public static LayoutTraverse build(Processor processor) {
        return new LayoutTraverse(processor);
    }

    public void traverse(ViewGroup root) {
        final int childCount = root.getChildCount();
        index++;
        for (int i = 0; i < childCount; ++i) {
            final View child = root.getChildAt(i);
            processor.process(child);

            if (child instanceof ViewGroup) {
                traverse((ViewGroup) child);
            }
        }

        index--;
        if (index == 0) {
            processor.traverseEnd(root); //遍历结束回调
        }
    }

}