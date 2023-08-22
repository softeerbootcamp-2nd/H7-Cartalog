import { URL } from '../../constants';

function wrapPromise(promise) {
  let status = 'pending';
  let response;

  const suspender = promise.then(
    (res) => {
      status = 'success';
      response = res;
    },
    (err) => {
      status = 'error';
      response = err;
    },
  );
  const read = () => {
    switch (status) {
      case 'pending':
        throw suspender;
      case 'error':
        throw response;
      default:
        return response;
    }
  };

  return { read };
}

function fetchData(path, { method = 'GET', body } = {}) {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
    body: (body && JSON.stringify(body)) || undefined,
  };
  const promise = fetch(`${URL}${path}`, options).then((res) => res.json());

  return wrapPromise(promise);
}

export default fetchData;
