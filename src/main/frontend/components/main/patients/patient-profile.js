import { LitElement, html, css } from 'lit';

class PatientProfile extends LitElement {
  static get styles() {
    return [css`
      :host {
        display: flex;
        flex: 0!important;
        justify-content: space-around;
        align-items: flex-start;
        flex-wrap: wrap;
        width: 80%;
        margin: 0 auto;
      }

      .full-name {
        order: 1;
        width: 100%;
        margin-bottom: 30px;
      }

      .name-wrapper {
        margin-right: 8px;
        display: inline-block;
      }

      .patient-data {
        order: 2;
        width: 60%
      }

      .patient-data tr {
        height: 40px;
        line-height: 40px;
      }

      .patient-data td {
        padding: 0 20px;
      }

      .patient-data tr:nth-child(odd) {
        background: #FAFAFA;
      }

      .label {
        text-transform: uppercase;
        color: #B5B9BC;
      }

      .name-wrapper {
        font-size: 0.8em;
      }

      .name-wrapper .name {
        font-size: 24px;
        font-weight: 600;
        line-height: 40px;
      }

      .name-wrapper .name.first {
        text-decoration: underline;
      }

      .profile-pic {
        order: 3;
        width: 35%;
        line-height: 200px;
        text-align: center;
        background: #FAFAFA;
      }

      @media (max-width: 900px) {
        .container {
          flex-direction: column;
          align-items: center;
          width: 90%
        }
        .full-name {
          width: auto;
        }
        .patient-data {
          width: 100%;
        }
        .profile-pic {
          width: 60%;
        }
        .patient-data .label {
          text-align: right;
        }
        .profile-pic {
          order: 0;
          margin-bottom: 20px;
        }
      }
    `];
  }

  static get is() { return 'patient-profile'; }

  static get properties() {
    return {
      patient: { type: Object }
    };
  }

  constructor() {
    super();
    this.patient = null;
  }

  render() {
    const p = this.patient || {};
    const doctor = p.doctor || {};
    return html`
      <div class="full-name">
        <div class="name-wrapper">
          <div class="label">First name</div>
          <div class="name first" id="firstName">${p.firstName}</div>
        </div>
        <div class="name-wrapper">
          <div class="label">Middle name</div>
          <div class="name" id="middleName">${p.middleName}</div>
        </div>
        <div class="name-wrapper">
          <div class="label">Last name</div>
          <div class="name" id="lastName">${p.lastName}</div>
        </div>
      </div>
      <table class="patient-data">
        <tbody>
          <tr>
            <td class="label">Gender</td>
            <td class="value" id="gender">${p.gender}</td>
          </tr>
          <tr>
            <td class="label">Date of birth</td>
            <td class="value" id="birthDate">${p.birthDate}</td>
          </tr>
          <tr>
            <td class="label">SSN</td>
            <td class="value" id="ssn">${p.ssn}</td>
          </tr>
          <tr>
            <td class="label">Patient Id</td>
            <td class="value" id="id">${p.id}</td>
          </tr>
          <tr>
            <td class="label">Doctor</td>
            <td class="value" id="doctor">${doctor.lastName}, ${doctor.firstName}</td>
          </tr>
          <tr>
            <td class="label">Medical Record</td>
            <td class="value" id="medicalRecord">${p.medicalRecord}</td>
          </tr>
          <tr>
            <td class="label">Last Visit</td>
            <td class="value" id="lastVisit">${p.lastVisit}</td>
          </tr>
        </tbody>
      </table>
      ${p.pictureUrl ? html`<img class="profile-pic" src="${p.pictureUrl}" alt="Patient photo">` : ''}
    `;
  }
}
customElements.define(PatientProfile.is, PatientProfile);
