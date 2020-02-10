import '@polymer/polymer/polymer-legacy.js';
import './my-icons.js';
import './shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class LoginView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
       :host {
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 2rem;
      }



      .login-box {
        width: 400px;
        position: relative;
      }



      .login-box h1 {
        color: #9DD22D;
        text-transform: uppercase;
        font-weight: 300;
      }



      .login-box button {
        width: 40%;
      }



      .alert.error {
        position: absolute;
        bottom: 0;
        transform: translateY(100px);
      }



      button {
        margin-top: 1rem;
      }
    </style> 
   <div class="login-box"> 
    <iron-a11y-keys keys="enter" on-keys-pressed="login"></iron-a11y-keys> 
    <div class="form"> 
     <h1>Patient portal</h1> 
     <div class="field stacked"> 
      <label for="username">Username</label> 
      <input autofocus="" id="username" type="text" autocomplete="username" value="{{username::input}}"> 
     </div> 
     <div class="field stacked"> 
      <label for="password">Password</label> 
      <input id="password" type="password" autocomplete="password" value="{{password::input}}"> 
     </div> 
     <vaadin-button id="login-button" class="primary">Login</vaadin-button> 
    </div> 
    <slot></slot> 
   </div> 
`;
  }

  static get is() { return 'login-view' }
}
customElements.define(LoginView.is, LoginView);

