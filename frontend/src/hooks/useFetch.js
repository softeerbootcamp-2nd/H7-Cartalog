import { useState, useEffect } from 'react';
import { URL } from '../constants';

function useFetch(path, { method = 'GET', body = null } = {}) {
  const [promise, setPromise] = useState();
  const [status, setStatus] = useState('pending');
  const [result, setResult] = useState();
  const [error, setError] = useState();

  function resolvePromise(promiseResult) {
    setStatus('fulfilled');
    setResult(promiseResult);
  }
  function rejectPromise(promiseError) {
    setStatus('error');
    setError(promiseError);
  }

  const fetchData = async () => {
    try {
      const config = {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      };
      const response = await fetch(`${URL}${path}`, body && config);

      const data = await response.json();

      resolvePromise(data);
    } catch (fetchError) {
      rejectPromise(fetchError);
    }
  };

  useEffect(() => {
    setPromise(fetchData());
    setStatus('pending');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (status === 'pending' && promise) throw promise;
  if (status === 'error') throw error;
  return result;
}

export default useFetch;
