import { useLayoutEffect, useRef, useState } from 'react';
import * as S from './style';

function PriceGraph({ min, max, avg, value, width, height }) {
  const svgRef = useRef();
  const [coords, setCoords] = useState([]);
  const drawWidth = width - 2;
  const drawHeight = height - 2;
  const avgX = ((avg - min) / (max - min)) * width;
  const drawAvgX = avgX - 2;
  const leftPX = drawAvgX / 2;
  const leftPathD = `M 2 ${drawHeight} C ${leftPX} ${drawHeight}, ${leftPX} 2, ${avgX} 2`;
  const rightPX = (drawWidth - avgX) / 2 + avgX;
  const rightPathD = `M ${avgX} 2 C ${rightPX} 2, ${rightPX} ${drawHeight}, ${drawWidth} ${drawHeight}`;

  useLayoutEffect(() => {
    const svg = svgRef.current;
    const [leftPath, rightPath] = svg.children;
    const overAvg = value > avg;
    const path = overAvg ? rightPath : leftPath;
    const length = path.getTotalLength();
    const valueRatio = overAvg ? (value - avg) / (max - avg) : (value - min) / (avg - min);
    const valueLength = length * valueRatio;
    const point = path.getPointAtLength(valueLength);

    setCoords([point.x, point.y]);
  }, [avg, max, min, value]);

  return (
    <S.PriceGraph>
      <S.PriceGraphSvg ref={svgRef} width={width} height={height} fill="none">
        <path d={leftPathD} />
        <path d={rightPathD} />
        <circle cx={coords[0]} cy={coords[1]} r={10} />
        <circle cx={coords[0]} cy={coords[1]} r={6} />
      </S.PriceGraphSvg>
      <div style={{ left: coords[0] + 20, top: coords[1] - 11 }}>내 견적</div>
    </S.PriceGraph>
  );
}

export default PriceGraph;
