import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-board/src/vaadin-board.js';

class Algo1A extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-board>
 <vaadin-board-row></vaadin-board-row>
 <vaadin-board-row></vaadin-board-row>
 <vaadin-board-row></vaadin-board-row>
 <vaadin-board-row></vaadin-board-row>
</vaadin-board>
`;
    }

    static get is() {
        return 'algo1-a';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(Algo1A.is, Algo1A);
