import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

/**
 * `algo-a`
 *
 * AlgoA element.
 *
 * @customElement
 * @polymer
 */
class AlgoA extends PolymerElement {

    static get template() {
        return html`
            <style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
        `;
    }

    static get is() {
        return 'algo-a';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(AlgoA.is, AlgoA);
