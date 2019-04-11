package com.hg.hollowgoods.UI.Base.Message.Dialog;

/**
 * 提交配置
 * Created by Hollow Goods on 2019-04-10.
 */
public class ConfigSubmit {

    public enum SubmitStatus {
        Wait,
        Request,
        Error,
        Success,
    }

    private String stepName;
    /**
     * 是否为不确定进度
     */
    private boolean isIndefinite;
    private int progress;
    private SubmitStatus status = SubmitStatus.Wait;

    public String getStepName() {
        return stepName;
    }

    public boolean isIndefinite() {
        return isIndefinite;
    }

    public int getProgress() {
        return progress;
    }

    public SubmitStatus getStatus() {
        return status;
    }

    public ConfigSubmit setStepName(String stepName) {
        this.stepName = stepName;
        return this;
    }

    public ConfigSubmit setIndefinite(boolean indefinite) {
        isIndefinite = indefinite;
        return this;
    }

    public ConfigSubmit setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public ConfigSubmit setStatus(SubmitStatus status) {
        this.status = status;
        return this;
    }
}
