package com.yanglx.dubbo.test.ui.setting;

import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.Function;
import com.intellij.util.ui.table.TableModelEditor;
import com.yanglx.dubbo.test.ui.AppSettingsComponent;
import com.yanglx.dubbo.test.ui.MyConfigurableDubboSettings;
import com.yanglx.dubbo.test.utils.StrUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class MyDialogItemEditor implements TableModelEditor.DialogItemEditor<MyConfigurableDubboSettings> {

    private AppSettingsComponent appSettingsComponent;

    public MyDialogItemEditor(AppSettingsComponent appSettingsComponent) {
        this.appSettingsComponent = appSettingsComponent;
    }


    @NotNull
    @Override
    public Class<MyConfigurableDubboSettings> getItemClass() {
        return MyConfigurableDubboSettings.class;
    }

    @Override
    public MyConfigurableDubboSettings clone(@NotNull MyConfigurableDubboSettings item, boolean forInPlaceEditing) {
        //对应工具栏得复制
        MyConfigurableDubboSettings myConfigurableDubboSettings = new MyConfigurableDubboSettings(forInPlaceEditing ? item.getId() : UUID.randomUUID());
        myConfigurableDubboSettings.setConfig(item.getName(), item.getProcessedAddress(), item.getVersion(), item.getGroup(), item.getUsername(), item.getPassword(), item.getTimeout());
        return myConfigurableDubboSettings;
    }

//    @Override
//    public void edit(@NotNull MyConfigurableDubboSettings item, @NotNull Function<? super MyConfigurableDubboSettings, ? extends MyConfigurableDubboSettings> mutator, boolean isAdd) {
//        //对应工具栏得添加或者编辑按钮事件
//        MyConfigurableDubboSettings settings = this.openDialog(item);
//        if (settings != null) {
//            mutator.fun(item).setConfig(settings.getName(), settings.getProcessedAddress(), settings.getVersion(), settings.getGroup());
//        }
//    }

    @Override
    public void edit(@NotNull MyConfigurableDubboSettings item, @NotNull Function<? super MyConfigurableDubboSettings, ? extends MyConfigurableDubboSettings> mutator, boolean isAdd) {
        //对应工具栏得添加或者编辑按钮事件
        MyConfigurableDubboSettings settings = this.openDialog(item, isAdd);
        if (settings != null) {
            if (isAdd) {
                mutator.fun(item).setConfig(settings.getName(), settings.getProcessedAddress(), settings.getVersion(), settings.getGroup(), item.getUsername(), item.getPassword(), item.getUsername());
            } else {
                mutator.fun(item).setConfig(settings.getName(), settings.getProcessedAddress(), settings.getVersion(), settings.getGroup(), item.getUsername(), item.getPassword(), item.getUsername(), item.getId());
            }
        }
    }

    @Override
    public void applyEdited(@NotNull MyConfigurableDubboSettings oldItem, @NotNull MyConfigurableDubboSettings newItem) {
        System.out.println("=====================applyEdited=====================");

    }

    @Override
    public boolean isUseDialogToAdd() {
        //设置为弹窗进行添加
        return true;
    }

    private MyConfigurableDubboSettings openDialog(MyConfigurableDubboSettings browser, boolean isAdd) {
        SettingDialog settingDialog = new SettingDialog(browser);
        final DialogBuilder dialogBuilder = new DialogBuilder(appSettingsComponent.getPanel())
                .title("Dubbo Setting").centerPanel(settingDialog.getPanel());
        if (dialogBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
            MyConfigurableDubboSettings myConfigurableDubboSettings = settingDialog.getMyConfigurableDubboSettings(browser);
            if (isExist(myConfigurableDubboSettings, isAdd)) {
                JLabel jLabel = new JLabel("Data duplication");
                DialogBuilder msgDialog = new DialogBuilder(appSettingsComponent.getPanel())
                        .title("Dubbo Setting").centerPanel(jLabel);
                msgDialog.show();
                return null;
            }
            browser.setProtocol(myConfigurableDubboSettings.getProtocol());
            browser.setIp(myConfigurableDubboSettings.getIp());
            browser.setPort(myConfigurableDubboSettings.getPort());
            browser.setVersion(myConfigurableDubboSettings.getVersion());
            browser.setGroup(myConfigurableDubboSettings.getGroup());
            browser.setName(myConfigurableDubboSettings.getName());
            browser.setTimeout(myConfigurableDubboSettings.getTimeout());
            browser.setUsername(myConfigurableDubboSettings.getUsername());
            browser.setPassword(myConfigurableDubboSettings.getPassword());
            if (StrUtils.isNotBlank(browser.getProcessedAddress())) {
                return browser;
            }
        }
        return null;
    }

    /**
     * 校验重复
     *
     * @param settings
     * @return
     */
    private boolean isExist(MyConfigurableDubboSettings settings, boolean isAdd) {
        List<MyConfigurableDubboSettings> settings1 = appSettingsComponent.getSettings();
        for (MyConfigurableDubboSettings dubboSettings : settings1) {
            if (dubboSettings.getId().equals(settings.getId())) {
                continue;
            }
            String item = dubboSettings.getName();
            String item2 = settings.getName();
            if (isAdd && item.equals(item2)) {
                return true;
            }
        }
        return false;
    }
}
