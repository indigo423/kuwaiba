import {OlMap} from '../ol-map.js';

const assert = chai.assert;

suite('ol-map', () => {
  test('is defined', () => {
    const el = document.createElement('ol-map');
    assert.instanceOf(el, OlMap);
  });
});
