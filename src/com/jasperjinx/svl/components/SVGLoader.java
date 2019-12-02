package com.jasperjinx.svl.components;

import javafx.scene.shape.SVGPath;

class SVGLoader {
    /**
     *
     * @param name Receive icon name
     * @return SVGPath
     */
    public static SVGPath getPathByName(SVGIcon name) {
        return new SVGPath() {{
            setContent(name.getPath());
        }};
    }

}
