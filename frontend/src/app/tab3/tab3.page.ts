import { Component, ViewChild } from '@angular/core';
import { IonModal } from '@ionic/angular';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss'],
})
export class Tab3Page {
  @ViewChild(IonModal) changePasswordModal: IonModal;
  constructor(private authService: AuthService) {}

  logout() {
    this.authService.logout();
  }

  passwordChangeEventHandler(isPasswordChanged: boolean) {
    this.changePasswordModal.dismiss();
    if (isPasswordChanged) {
      this.logout();
    }
  }
}
