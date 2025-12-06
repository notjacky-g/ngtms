package com.hwacom.ngtms.pd.am.presenter;

import com.hwacom.ngtms.pd.am.view.PdViewer;

public class PdPresenter {

  private PdViewer viewer;

  public PdPresenter(PdViewer viewer) {
    this.viewer = viewer;
    viewer.setPresenter(this);
  }
}
