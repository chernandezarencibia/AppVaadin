import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import './prueba-1.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-tabs/src/vaadin-tabs.js';
import '@vaadin/vaadin-tabs/src/vaadin-tab.js';

class Algo3 extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout class="header" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: var(--lumo-contrast-10pct);"></vaadin-horizontal-layout>

  </vaadin-tab><vaadin-button>
                  Secondary
                </vaadin-button>
                <vaadin-button>
                 <iron-icon icon="lumo:arrow-right" slot="suffix"></iron-icon> Next
                </vaadin-button>
                <vaadin-tabs>
                 <vaadin-tab>
                   Tab one
                 </vaadin-tab>
                 <vaadin-tab>
                   Tab two
                 </vaadin-tab>
                 <vaadin-tab>
                   Tab three
 </vaadin-tabs>
 <vaadin-tabs>
  <vaadin-tab>
   <iron-icon icon="lumo:user"></iron-icon>
   <span>Tab one</span>
  </vaadin-tab>
  <vaadin-tab>
   <iron-icon icon="lumo:cog"></iron-icon>
   <span>Tab two</span>
  </vaadin-tab>
  <vaadin-tab>
   <iron-icon icon="lumo:bell"></iron-icon>
   <span>Tab three</span>
  </vaadin-tab>
 </vaadin-tabs>
 <prueba-1></prueba-1>
 <vaadin-horizontal-layout style="width: 100%; flex-grow: 1; flex-shrink: 1; flex-basis: auto;">
  <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(7*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-5pct);"></vaadin-vertical-layout>
  <vaadin-vertical-layout class="content" style="flex-grow: 1; flex-shrink: 1; flex-basis: auto;"></vaadin-vertical-layout>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="footer" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: var(--lumo-contrast-10pct);"></vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'algo-3';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(Algo3.is, Algo3);
