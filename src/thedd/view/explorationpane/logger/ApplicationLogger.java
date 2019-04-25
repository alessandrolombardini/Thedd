package thedd.view.explorationpane.logger;

import javafx.beans.property.DoubleProperty;

/**
 * A graphic component which can show the result of an action.
 * It is managed by a {@link LoggerManager} which can run parallel to the application.
 */
public interface ApplicationLogger {

    /**
     * Set the current text to show.
     * @param text
     *          the text to show
     */
    void setText(String text);

    /**
     * Set whether the logger is visible or not.
     * @param isVisible
     *          whether the logger is visible
     */
    void setVisibility(boolean isVisible);

    /**
     * Set the {@link LoggerManager} which will manage this Logger.
     * @param logMan
     *          the new LoggerManager 
     */
    void setLoggerManager(LoggerManager logMan);

    /**
     * Get the width property of this graphical component.
     * @return
     *          the width {@link DoubleProperty} 
     */
    DoubleProperty getWidthProperty();

    /**
     * Get the height property of this graphical component.
     * @return
     *          the height {@link DoubleProperty}
     */
    DoubleProperty getHeightProperty();

    /**
     * The translateX property of this graphical component.
     * It represent the distance between the x coordinates of the parent and this component.
     * @return
     *          the translateX {@link DoubleProperty}
     */
    DoubleProperty translateXProperty();

    /**
     * The translateY property of this graphical component.
     * It represent the distance between the y coordinates of the parent and this component.
     * @return
     *          the translateY {@link DoubleProperty}
     */
    DoubleProperty translateYProperty();

}
