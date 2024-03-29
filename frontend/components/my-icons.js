const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<iron-iconset-svg name="icons" size="24"> 
  <svg> 
   <defs> 
    <g id="close">
     <path d="M15.1 3.1l-2.2-2.2-4.9 5-4.9-5-2.2 2.2 5 4.9-5 4.9 2.2 2.2 4.9-5 4.9 5 2.2-2.2-5-4.9z"></path>
    </g> 
    <g id="lock">
     <path d="M12 8v-3.1c0-2.2-1.6-3.9-3.8-3.9h-0.3c-2.1 0-3.9 1.7-3.9 3.9v3.1h-1l0.1 5c0 0-0.1 3 4.9 3s5-3 5-3v-5h-1zM9 14h-1v-2c-0.6 0-1-0.4-1-1s0.4-1 1-1 1 0.4 1 1v3zM10 8h-4v-3.1c0-1.1 0.9-1.9 1.9-1.9h0.3c1 0 1.8 0.8 1.8 1.9v3.1z"></path>
    </g> 
    <g id="sign-out">
     <path d="M9 4v-3h-9v14h9v-3h-1v2h-7v-12h7v2z"></path>
     <path d="M16 8l-5-4v2h-5v4h5v2z"></path>
    </g> 
    <g id="plus">
     <path d="M14 7h-5v-5h-2v5h-5v2h5v5h2v-5h5v-2z"></path>
    </g> 
    <g id="chevron-down">
     <path d="M8 13.1l-8-8 2.1-2.2 5.9 5.9 5.9-5.9 2.1 2.2z"></path>
    </g> 
    <g id="chevron-right">
     <path d="M13.1 8l-8 8-2.2-2.1 5.9-5.9-5.9-5.9 2.2-2.1z"></path>
    </g> 
    <g id="arrow-long-left">
     <path d="M15 7v2h-11v2l-3-3 3-3v2z"></path>
    </g> 
   </defs> 
  </svg> 
 </iron-iconset-svg>`;

document.head.appendChild($_documentContainer.content);

