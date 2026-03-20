import { LitElement, html, css } from 'lit';
import { customElement } from 'lit/decorators.js';
import { sharedStyles } from './shared-styles.js';
import '@vaadin/button';

@customElement('login-view')
class LoginView extends LitElement {
  declare $server: { login(username: string, password: string): void };

  static get styles() {
    return [sharedStyles, css`
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
    `];
  }

  render() {
    return html`
      <div class="login-box" @keydown="${(e: KeyboardEvent) => e.key === 'Enter' && this._login()}">
        <div class="form">
          <h1>Patient portal</h1>
          <div class="field stacked">
            <label for="username">Username</label>
            <input autofocus id="username" type="text" autocomplete="username" value="user">
          </div>
          <div class="field stacked">
            <label for="password">Password</label>
            <input id="password" type="password" autocomplete="password" value="password">
          </div>
          <vaadin-button id="login-button" class="primary" @click="${() => this._login()}">Login</vaadin-button>
        </div>
        <slot></slot>
      </div>
    `;
  }

  private _login() {
    const username = (this.shadowRoot!.querySelector('#username') as HTMLInputElement).value;
    const password = (this.shadowRoot!.querySelector('#password') as HTMLInputElement).value;
    this.$server.login(username, password);
  }
}
