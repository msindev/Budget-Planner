import { Component, ViewChild } from '@angular/core';
import { IonModal } from '@ionic/angular';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-tab-profile',
  templateUrl: 'profile.page.html',
  styleUrls: ['profile.page.scss'],
})
export class ProfilePageComponent {
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
