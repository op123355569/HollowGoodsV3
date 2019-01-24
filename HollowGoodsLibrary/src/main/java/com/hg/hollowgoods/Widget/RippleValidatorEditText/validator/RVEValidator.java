package com.hg.hollowgoods.Widget.RippleValidatorEditText.validator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hg.hollowgoods.Widget.RippleValidatorEditText.RVEValidatorType;

public abstract class RVEValidator {
    /**
     * Error message that the view will display if validation fails.
     * <p>
     * This is protected, so you can change this dynamically in your {@link #isValid(CharSequence)}
     * implementation. If necessary, you can also interact with this via its getter and setter.
     */
    protected String mErrorMessage;
    protected int type;
    private Object item;

    public RVEValidator(@RVEValidatorType.VType int type, @NonNull String errorMessage, @Nullable Object item) {
        this.type = type;
        this.mErrorMessage = errorMessage;
        this.item = item;
    }

    public void setErrorMessage(@NonNull String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    @NonNull
    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    @RVEValidatorType.VType
    public int getType() {
        return type;
    }

    public void setType(@RVEValidatorType.VType int type) {
        this.type = type;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    /**
     * Abstract method to implement your own validation checking.
     *
     * @param text The CharSequence representation of the text in the EditText field. Cannot be null, but may be empty.
     * @return True if valid, false if not
     */
    public abstract boolean isValid(@NonNull CharSequence text);

}
