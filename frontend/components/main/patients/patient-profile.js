import '@polymer/polymer/polymer-legacy.js';
import '@polymer/polymer/polymer-legacy.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<dom-module id="patient-profile" include="shared-styles.html"> 
  <template> 
   <style>
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
    </style> 
   <div class="full-name"> 
    <div class="name-wrapper"> 
     <div class="label">
      First name
     </div> 
     <div class="name first" id="firstName">
      [[patient.firstName]]
     </div> 
    </div> 
    <div class="name-wrapper"> 
     <div class="label">
      Middle name
     </div> 
     <div class="name" id="middleName">
      [[patient.middleName]]
     </div> 
    </div> 
    <div class="name-wrapper"> 
     <div class="label">
      Last name
     </div> 
     <div class="name" id="lastName">
      [[patient.lastName]]
     </div> 
    </div> 
   </div> 
   <table class="patient-data"> 
    <tbody>
     <tr> 
      <td class="label">Gender</td> 
      <td class="value" id="gender">[[patient.gender]]</td> 
     </tr> 
     <tr> 
      <td class="label">Date of birth</td> 
      <td class="value" id="birthDate">[[patient.birthDate]]</td> 
     </tr> 
     <tr> 
      <td class="label">SSN</td> 
      <td class="value" id="ssn">[[patient.ssn]]</td> 
     </tr> 
     <tr> 
      <td class="label">Patient Id</td> 
      <td class="value" id="id">[[patient.id]]</td> 
     </tr> 
     <tr> 
      <td class="label">Doctor</td> 
      <td class="value" id="doctor">[[patient.doctor.lastName]], [[patient.doctor.firstName]]</td> 
     </tr> 
     <tr> 
      <td class="label">Medical Record</td> 
      <td class="value" id="medicalRecord">[[patient.medicalRecord]]</td> 
     </tr> 
     <tr> 
      <td class="label">Last Visit</td> 
      <td class="value" id="lastVisit">[[patient.lastVisit]]</td> 
     </tr> 
    </tbody>
   </table> 
   <template is="dom-if" if="[[patient.pictureUrl]]"> 
    <img class="profile-pic" src="[[patient.pictureUrl]]" alt="Patient photo"> 
   </template> 
  </template> 
   
 </dom-module>`;

document.head.appendChild($_documentContainer.content);
class PatientProfile extends PolymerElement {
  static get is() { return 'patient-profile' }
}
customElements.define(PatientProfile.is, PatientProfile);

