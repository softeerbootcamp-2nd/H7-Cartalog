import { useEffect, useRef, useState } from 'react';

const useIntersectionObserver = (targetRef) => {
  const [isInViewport, setIsInViewport] = useState(false);
  const observerRef = useRef();

  useEffect(() => {
    const observerCallback = (entries) => {
      setIsInViewport(entries[0].isIntersecting);
    };

    if (!observerRef.current) {
      observerRef.current = new window.IntersectionObserver(observerCallback, {
        threshold: 0,
      });
    }

    if (targetRef.current) {
      observerRef.current.observe(targetRef.current);
    }

    return () => {
      if (observerRef.current) {
        observerRef.current.disconnect();
      }
    };
  }, [targetRef]);

  return isInViewport;
};

export default useIntersectionObserver;
