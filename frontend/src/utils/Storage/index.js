function Storage({ dataName, dataLink, trimId }) {
  async function fetchData() {
    try {
      if (dataName === 'initData') {
        const storedData = JSON.parse(localStorage.getItem(dataName));
        if (!storedData) {
          const response = await fetch(dataLink);
          const dataFetch = await response.json();
          localStorage.setItem('initData', JSON.stringify({ ...dataFetch }));
        }
      }
      // 타입일 경우 테스트
      if (dataName === 'modelType') {
        const storedData = JSON.parse(localStorage.getItem('initData')).trims[trimId - 1].modelType;
        if (!storedData) {
          const pushData = JSON.parse(localStorage.getItem('initData'));
          const response = await fetch(dataLink);
          const dataFetch = await response.json();
          pushData.trims[trimId - 1].modelType = dataFetch.modelTypes;
          localStorage.setItem('initData', JSON.stringify(pushData));
        }
      }
      // 외장색상 테스트
      if (dataName === 'exteriorColor') {
        const storedData = JSON.parse(localStorage.getItem('initData')).trims[trimId - 1].modelType
          .exteriorColor;

        if (!storedData) {
          console.log('안넝');
          const pushData = JSON.parse(localStorage.getItem('initData'));
          const response = await fetch(dataLink);
          const dataFetch = await response.json();
          const test = { ...dataFetch };
          pushData.trims[trimId - 1].modelType.exteriorColor = test.trimExteriorColorDtoList;
          localStorage.setItem('initData', JSON.stringify(pushData));
        }
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }
  fetchData(); // 함수 호출
}

export default Storage;
