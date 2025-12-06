package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.room.am.util.StringConverter;
import com.hwacom.ngtms.room.shared.CardType;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;

public class RoomCardCreateWidget extends Composite {

  private static RoomCardCreateWidgetUiBinder uiBinder =
      GWT.create(RoomCardCreateWidgetUiBinder.class);

  interface RoomCardCreateWidgetUiBinder extends UiBinder<Widget, RoomCardCreateWidget> {}

  @UiField TextField aba;

  @UiField ComboBox<CardType> cardTypeComboBox;

  @UiField(provided = true)
  ListStore<CardType> cardTypeCbStore;

  @UiField(provided = true)
  LabelProvider<CardType> cardTypeCbLebelProvider;

  public RoomCardCreateWidget() {

    cardTypeCbStore =
        new ListStore<>(
            new ModelKeyProvider<CardType>() {

              @Override
              public String getKey(CardType item) {
                return item.name();
              }
            });

    cardTypeCbLebelProvider =
        new LabelProvider<CardType>() {

          @Override
          public String getLabel(CardType item) {
            return StringConverter.getCardType(item);
          }
        };

    initWidget(uiBinder.createAndBindUi(this));

    fillCardType();
  }

  private void fillCardType() {
    cardTypeCbStore.add(CardType.REGULAR);
    cardTypeCbStore.add(CardType.TEMPORARY);
    cardTypeCbStore.add(CardType.UNLIMITE);
    cardTypeComboBox.setValue(cardTypeCbStore.get(0));
  }
}
